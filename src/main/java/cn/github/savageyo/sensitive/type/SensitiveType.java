package cn.github.savageyo.sensitive.type;

/**
 * 脱敏类型
 */
public enum SensitiveType {
  /**
   * 不脱敏
   */
  NONE,
  /**
   * 默认脱敏方式
   */
  DEFAULT,
  /**
   * 身份证号
   */
  ID_CARD,
  /**
   * 手机号
   */
  MOBILE_PHONE,
  /**
   * 地址
   */
  ADDRESS,
  /**
   * 电子邮件
   */
  EMAIL,
  /**
   * 银行卡
   */
  BANK_CARD,
  /**
   * 银行联号
   */
  UNION_BANK_NO,
  /**
   * 中文姓名
   */
  CHINESE_NAME
}
