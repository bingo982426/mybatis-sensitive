package cn.github.savageyo.sensitive.type.handler;


import cn.github.savageyo.sensitive.type.SensitiveType;
import cn.github.savageyo.sensitive.type.SensitiveTypeHandler;
import cn.hutool.core.util.StrUtil;

/**
 * 身份证号脱敏类型
 * 前3位，后4位
 * 130722199102323232 脱敏后: 130*************3232
 */
public class IDCardSensitiveHandler implements SensitiveTypeHandler {

  @Override
  public SensitiveType getSensitiveType() {
    return SensitiveType.ID_CARD;
  }

  @Override
  public String handle(Object src) {
    if (StrUtil.isEmptyIfStr(src)) {
      return null;
    }
    String idCard = src.toString();
    return idCard.substring(0, 3).concat(StrUtil.fillAfter("", '*', 11))
      .concat(idCard.substring(14));
  }
}
