package cn.github.savageyo.sensitive.type.handler;


import cn.github.savageyo.sensitive.type.SensitiveType;
import cn.github.savageyo.sensitive.type.SensitiveTypeHandler;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 身份证号脱敏类型,同时支持15&18位身份证号
 * 前3位，后4位
 * 130722199102323232 脱敏后: 130*************3232
 */
public class IDCardSensitiveHandler implements SensitiveTypeHandler {

  private static final int HEAD_LENGTH = 3;
  private static final int TAIL_LENGTH = 4;

  @Override
  public SensitiveType getSensitiveType() {
    return SensitiveType.ID_CARD;
  }

  @Override
  public String handle(Object src) {
    if (ObjectUtil.isEmpty(src) || !(src instanceof CharSequence)) {
      return null;
    }
    String idCard = src.toString();
    int length = idCard.length();
    return StrUtil.padAfter(idCard.substring(0, HEAD_LENGTH), length - TAIL_LENGTH,
      SENSITIVE_SYMBOL).concat(idCard.substring(length - TAIL_LENGTH));
  }
}
