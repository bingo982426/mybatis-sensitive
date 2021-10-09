package cn.github.savageyo.sensitive.interceptor;

import cn.github.savageyo.sensitive.annotation.EncryptField;
import cn.github.savageyo.sensitive.annotation.SensitiveEncrypt;
import cn.github.savageyo.sensitive.config.EncryptProperty;
import cn.github.savageyo.sensitive.encrypt.EncryptRegistry;
import cn.github.savageyo.sensitive.encrypt.EncryptType;
import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Table;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import javax.persistence.Column;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.entity.Example.Criterion;

/**
 * 拦截写请求的插件。插件生效仅支持预编译的sql
 */
@Intercepts({
  @Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class),
})
@Slf4j
@Component
public class SensitiveAndEncryptWriteInterceptor implements Interceptor {

  public static final String PACKAGE_NAME = "cn.github.savageyo";
  final
  EncryptProperty encryptProperty;

  public SensitiveAndEncryptWriteInterceptor(
    EncryptProperty encryptProperty) {
    this.encryptProperty = encryptProperty;
  }

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    if (!(invocation.getTarget() instanceof ParameterHandler)) {
      return invocation.proceed();
    }
    final ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
    final MetaObject metaObject = SystemMetaObject.forObject(parameterHandler);
    if (Objects.isNull(metaObject)) {
      return invocation.proceed();
    }
    final MappedStatement mappedStatement = (MappedStatement) metaObject.getValue(
      "mappedStatement");
    Object originalObject = parameterHandler.getParameterObject();
    if (SqlCommandType.INSERT.equals(mappedStatement.getSqlCommandType())
      || SqlCommandType.UPDATE.equals(mappedStatement.getSqlCommandType())
      || SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
      AnalysisType(originalObject);
    }
    return invocation.proceed();
  }

  private void AnalysisType(Object originalObject) throws IllegalAccessException {
    if (isProjectModel(originalObject)) {
      handleParam(originalObject);
    }
    if (originalObject instanceof ParamMap) {
      ParamMap<?> paramMap = (ParamMap<?>) originalObject;
      Optional<?> any = paramMap.values().stream().filter(this::isProjectModel).findAny();
      if (!any.isPresent()) {
        return;
      }
      Object object = any.get();
      if (object instanceof Collection) {
        List<?> temp = (List<?>) object;
        for (Object o : temp) {
          handleParam(o);
        }
      } else if (isProjectModel(object)) {
        handleParam(object);
      } else {
        log.debug("未知类型");
      }
    }
    if (originalObject instanceof Collection) {
      List<?> temp = (List<?>) originalObject;
      for (Object object : temp) {
        handleParam(object);
      }
    }
    if (originalObject instanceof Example) {
      Example example = (Example) originalObject;
      Class<?> entityClass = example.getEntityClass();
      Map<String, EntityColumn> propertyMap = example.getPropertyMap();
      BiMap<String, String> propertyBiMap = changeMap(propertyMap);
      if (isProjectModel(entityClass) && encryptModel(entityClass)) {
        Table<String, String, EncryptType> allEncryptProperty = getAllEncryptProperty(entityClass);
        List<Criteria> oredCriteriaList = example.getOredCriteria();
        for (Criteria criteria : oredCriteriaList) {
          List<Criterion> criterionList = criteria.getAllCriteria();
          for (Criterion criterion : criterionList) {
            String condition = criterion.getCondition();
            String property = condition.substring(0, condition.indexOf(" "));
            EncryptType encryptType = allEncryptProperty.get(propertyBiMap.get(property), property);
            if (null != encryptType) {
              String encrypt = EncryptRegistry.getEncryptType(encryptType, encryptProperty)
                .encrypt(criterion.getValue().toString());
              ReflectUtil.setFieldValue(criterion, "value", encrypt);
            }
          }
        }
        Field oredCriteria = ReflectUtil.getField(originalObject.getClass(), "oredCriteria");
        ReflectUtil.setFieldValue(originalObject, oredCriteria, oredCriteriaList);
      }
    }
  }

  private BiMap<String, String> changeMap(Map<String, EntityColumn> propertyMap) {
    BiMap<String, String> propertyBiMap = HashBiMap.create();
    propertyMap.forEach((k, v) -> propertyBiMap.put(v.getColumn(), k));
    return propertyBiMap;
  }

  private Table<String, String, EncryptType> getAllEncryptProperty(Class<?> entityClass) {
    Field[] fields = ReflectUtil.getFields(entityClass);
    Table<String, String, EncryptType> propertyTable = HashBasedTable.create();
    for (Field field : fields) {
      EncryptField annotation = field.getAnnotation(EncryptField.class);
      if (null != annotation) {
        Column column = field.getAnnotation(Column.class);
        propertyTable.put(field.getName(), column.name(), annotation.encryptType());
      }
    }
    return propertyTable;
  }

  private boolean isProjectModel(Object object) {
    if (object instanceof Collection) {
      return ((List) object).get(0).getClass().getName().startsWith(PACKAGE_NAME);
    }
    if (object instanceof Class) {
      return ((Class) object).getName().startsWith(PACKAGE_NAME);
    }
    return object.getClass().getName().startsWith(PACKAGE_NAME);
  }

  private boolean encryptModel(Class<?> entityClass) throws IllegalAccessException {
    SensitiveEncrypt sensitiveEncrypt = entityClass.getAnnotation(
      SensitiveEncrypt.class);
    return null != sensitiveEncrypt;
  }

  private void handleParam(Object parameterObject) throws IllegalAccessException {
    Class<?> parameterObjectClass = parameterObject.getClass();
    SensitiveEncrypt sensitiveEncrypt = parameterObjectClass.getAnnotation(
      SensitiveEncrypt.class);
    if (null == sensitiveEncrypt) {
      return;
    }
    Field[] fields = ReflectUtil.getFields(parameterObjectClass);
    for (Field field : fields) {
      EncryptField encryptField = field.getAnnotation(EncryptField.class);
      if (null == encryptField) {
        continue;
      }
      field.setAccessible(true);
      Object object = field.get(parameterObject);
      if (object instanceof String) {
        field.set(parameterObject,
          EncryptRegistry.getEncryptType(encryptField.encryptType(), encryptProperty)
            .encrypt(object.toString()));
      }
    }
  }

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {
  }
}
