package cn.github.savageyo.sensitive.interceptor;

import cn.github.savageyo.sensitive.annotation.EncryptField;
import cn.github.savageyo.sensitive.annotation.SensitiveEncrypt;
import cn.github.savageyo.sensitive.config.EncryptProperty;
import cn.github.savageyo.sensitive.encrypt.EncryptRegistry;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
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

/**
 * 拦截写请求的插件。插件生效仅支持预编译的sql
 */
@Intercepts({
  @Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class),
})
@Slf4j
@Component
public class SensitiveAndEncryptWriteInterceptor implements Interceptor {

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
    final Object originalObject = parameterHandler.getParameterObject();
    if (SqlCommandType.INSERT.equals(mappedStatement.getSqlCommandType())
      || SqlCommandType.UPDATE.equals(mappedStatement.getSqlCommandType())) {
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
      Optional<?> any = paramMap.values().stream()
        .filter(this::isProjectModel)
        .findAny();
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
  }

  private boolean isProjectModel(Object object) {
    if (object instanceof Collection) {
      return ((List) object).get(0).getClass().getName().startsWith("cn.github.savageyo");
    }
    return object.getClass().getName().startsWith("cn.github.savageyo");
  }

  private void handleParam(Object parameterObject) throws IllegalAccessException {
    Class<?> parameterObjectClass = parameterObject.getClass();
    SensitiveEncrypt sensitiveEncrypt = parameterObjectClass.getAnnotation(
      SensitiveEncrypt.class);
    if (null == sensitiveEncrypt) {
      return;
    }
    Field[] fields = parameterObjectClass.getDeclaredFields();
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
