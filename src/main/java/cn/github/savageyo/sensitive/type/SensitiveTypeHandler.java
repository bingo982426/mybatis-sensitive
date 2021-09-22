package cn.github.savageyo.sensitive.type;

import cn.hutool.core.util.ObjectUtil;

/**
 * 脱敏处理类
 */
public interface SensitiveTypeHandler {

  char SENSITIVE_SYMBOL_CHAR = '*';

  default boolean needHandler(Object src) {
    return ObjectUtil.isNotEmpty(src) && src instanceof CharSequence;
  }

  /**
   * 获取脱敏的类型枚举
   */
  SensitiveType getSensitiveType();

  /**
   * 对数据的值进行脱敏处理
   *
   * @param src src
   * @return 脱敏后的数据
   */
  String handle(Object src);
}
