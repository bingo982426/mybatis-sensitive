package cn.github.savageyo.sensitive.interceptor;

import cn.github.savageyo.sensitive.annotation.EncryptField;
import cn.github.savageyo.sensitive.annotation.SensitiveBind;
import cn.github.savageyo.sensitive.annotation.SensitiveEncrypt;
import cn.github.savageyo.sensitive.annotation.SensitiveField;
import cn.github.savageyo.sensitive.config.EncryptProperty;
import cn.github.savageyo.sensitive.encrypt.EncryptRegistry;
import cn.github.savageyo.sensitive.helper.SensitiveHelper;
import cn.github.savageyo.sensitive.type.SensitiveType;
import cn.github.savageyo.sensitive.type.SensitiveTypeRegistry;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;


/**
 * 对响应结果进行拦截处理,对需要解密的字段进行解密
 */
@Intercepts({
  @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {
    java.sql.Statement.class})
})
@Slf4j
@Component
public class SensitiveAndDecryptReadInterceptor implements Interceptor {

  private static final String MAPPED_STATEMENT = "mappedStatement";
  final
  EncryptProperty encryptProperty;

  public SensitiveAndDecryptReadInterceptor(
    EncryptProperty encryptProperty) {
    this.encryptProperty = encryptProperty;
  }

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    final List<Object> results = (List<Object>) invocation.proceed();
    if (CollUtil.isEmpty(results)) {
      return results;
    }
    Object result = results.get(0);
    SensitiveEncrypt sensitiveEncrypt = result.getClass()
      .getAnnotation(SensitiveEncrypt.class);
    if (sensitiveEncrypt == null || !sensitiveEncrypt.value()) {
      return results;
    }
    final ResultSetHandler statementHandler = (ResultSetHandler) invocation.getTarget();
    final MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
    final MappedStatement mappedStatement = (MappedStatement) metaObject.getValue(MAPPED_STATEMENT);
    final ResultMap resultMap =
      mappedStatement.getResultMaps().isEmpty() ? null : mappedStatement.getResultMaps().get(0);
    if (resultMap == null) {
      return new HashMap<>(16);
    }
    Map<String, EncryptField> encryptFieldMap = new HashMap<>(16);
    Map<String, SensitiveBind> sensitiveBindMap = new HashMap<>(16);
    Map<String, SensitiveField> sensitiveFieldMap = new HashMap<>(16);
    Set<String> annotationFieldList = getAnnotationMap(resultMap, encryptFieldMap,
      sensitiveBindMap, sensitiveFieldMap);
    if (MapUtil.isEmpty(encryptFieldMap) && MapUtil.isEmpty(sensitiveBindMap) && MapUtil.isEmpty(
      sensitiveFieldMap)) {
      return results;
    }
    results.parallelStream().forEach(e -> {
      MetaObject objMetaObject = mappedStatement.getConfiguration().newMetaObject(e);
      Map<String, Object> beanMap = BeanUtil.beanToMap(e);
      annotationFieldList.forEach(field -> {
        EncryptField encryptField = encryptFieldMap.get(field);
        if (null != encryptField) {
          String decryptValue = EncryptRegistry.getEncryptType(encryptField.encryptType(),
            encryptProperty).decrypt(beanMap.get(field).toString());
          objMetaObject.setValue(field, decryptValue);
          beanMap.put(field, decryptValue);
        }
        if (null == SensitiveHelper.getSensitiveFLag()) {
          SensitiveBind sensitiveBind = sensitiveBindMap.get(field);
          if (null != sensitiveBind) {
            String value = (String) beanMap.get(sensitiveBind.bindField());
            SensitiveType sensitiveType = sensitiveBind.value();
            value = SensitiveTypeRegistry.get(sensitiveType).handle(value);
            objMetaObject.setValue(field, value);
            beanMap.put(field, value);
          }
          SensitiveField sensitiveField = sensitiveFieldMap.get(field);
          if (null != sensitiveField) {
            String sensitiveValue = SensitiveTypeRegistry.get(sensitiveField.value())
              .handle(beanMap.get(field).toString());
            objMetaObject.setValue(field, sensitiveValue);
            beanMap.put(field, sensitiveValue);
          }
        }
      });
    });
    SensitiveHelper.clean();
    return results;
  }

  private Set<String> getAnnotationMap(ResultMap resultMap,
    Map<String, EncryptField> encryptFieldMap,
    Map<String, SensitiveBind> sensitiveBindMap, Map<String, SensitiveField> sensitiveFieldMap) {
    Set<String> annotationFieldList = new HashSet<>(16);
    Class<?> clazz = resultMap.getType();
    for (Field field : clazz.getDeclaredFields()) {
      Annotation[] annotations = field.getAnnotations();
      for (Annotation annotation : annotations) {
        if (annotation instanceof EncryptField) {
          EncryptField encryptField = field.getAnnotation(EncryptField.class);
          encryptFieldMap.put(field.getName(), encryptField);
          annotationFieldList.add(field.getName());
        }
        if (annotation instanceof SensitiveBind) {
          SensitiveBind sensitiveBind = field.getAnnotation(SensitiveBind.class);
          sensitiveBindMap.put(field.getName(), sensitiveBind);
          annotationFieldList.add(field.getName());
        }
        if (annotation instanceof SensitiveField) {
          SensitiveField sensitiveField = field.getAnnotation(SensitiveField.class);
          sensitiveFieldMap.put(field.getName(), sensitiveField);
          annotationFieldList.add(field.getName());
        }
      }
    }
    return annotationFieldList;
  }

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {
  }
}
