package cn.github.savageyo.sensitive.annotation;

import cn.github.savageyo.sensitive.type.SensitiveType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解适用于如下场景：
 * 例如，数据库只存了username字段的加密信息，没有脱敏冗余字段。
 * 我的响应类里希望将数据库的加密的某个字段映射到响应的两个属性上（一个解密的，一个脱敏的）就可以使用该注解。
 * 例如，POJO里有如下字段
 * EncryptField
 * private String username
 * <p>
 * SensitiveBind(bindField = "userName",value = SensitiveType.CHINESE_NAME)
 * private String userNameOnlyDTO;
 * <p>
 * <p>
 * 则当查询出结果时，userNameOnlyDTO会赋值为username解密后再脱敏的值。
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SensitiveBind {

  /**
   * 该属性从哪个字段取得
   *
   * @return 返回字段名
   */
  String bindField();

  /**
   * 脱敏类型
   * 不同的脱敏类型置换*的方式不同
   *
   * @return SensitiveType
   */
  SensitiveType value();

}
