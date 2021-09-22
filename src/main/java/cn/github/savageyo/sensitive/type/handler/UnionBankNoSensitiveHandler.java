package cn.github.savageyo.sensitive.type.handler;


import cn.github.savageyo.sensitive.type.SensitiveType;
import cn.github.savageyo.sensitive.type.SensitiveTypeHandler;
import cn.hutool.core.util.StrUtil;

/**
 * 联行号脱敏
 * 保留前4位
 */
public class UnionBankNoSensitiveHandler implements SensitiveTypeHandler {

  private static final int HEAD_LENGTH = 4;

  @Override
  public SensitiveType getSensitiveType() {
    return SensitiveType.UNION_BANK_NO;
  }

  @Override
  public String handle(Object src) {
    if (!needHandler(src)) {
      return null;
    }
    String snapCard = src.toString();
    return StrUtil.padAfter(snapCard.substring(0, HEAD_LENGTH), snapCard.length(),
      SENSITIVE_SYMBOL_CHAR);
  }
}
