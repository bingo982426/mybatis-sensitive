package cn.github.savageyo.sensitive.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记在POJO上，用于说明是否支持加解密
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SensitiveEncrypt {

  /**
   * 是否开启加解密和脱敏模式
   *
   * @return SensitiveEncryptEnabled
   */
  boolean value() default true;
}
