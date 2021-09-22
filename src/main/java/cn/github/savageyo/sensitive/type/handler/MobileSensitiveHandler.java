package cn.github.savageyo.sensitive.type.handler;


import cn.github.savageyo.sensitive.type.SensitiveType;
import cn.github.savageyo.sensitive.type.SensitiveTypeHandler;
import cn.hutool.core.util.StrUtil;

/**
 * 手机号脱敏处理类
 * 18233583070 脱敏后: 182****3030
 */
public class MobileSensitiveHandler implements SensitiveTypeHandler {

  private static final int HEAD_LENGTH = 3;
  private static final int TAIL_LENGTH = 4;

  @Override
  public SensitiveType getSensitiveType() {
    return SensitiveType.MOBILE_PHONE;
  }

  @Override
  public String handle(Object src) {
    if (!needHandler(src)) {
      return null;
    }
    String mobile = src.toString();
    int length = mobile.length();
    return StrUtil.padAfter(mobile.substring(0, HEAD_LENGTH), length - TAIL_LENGTH,
      SENSITIVE_SYMBOL_CHAR).concat(mobile.substring(length - TAIL_LENGTH));
  }
}
