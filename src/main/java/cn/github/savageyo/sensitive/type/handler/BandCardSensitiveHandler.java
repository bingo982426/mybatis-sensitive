package cn.github.savageyo.sensitive.type.handler;


import cn.github.savageyo.sensitive.type.SensitiveType;
import cn.github.savageyo.sensitive.type.SensitiveTypeHandler;
import cn.hutool.core.util.StrUtil;

/**
 * 银行卡号脱敏
 * 只留前四位和后四位
 * 6227 0383 3938 3938 393 脱敏结果: 6227 **** **** ***8 393
 */
public class BandCardSensitiveHandler implements SensitiveTypeHandler {

  @Override
  public SensitiveType getSensitiveType() {
    return SensitiveType.BANK_CARD;
  }

  @Override
  public String handle(Object src) {
    if (StrUtil.isEmptyIfStr(src)) {
      return null;
    }
    String bankCard = StrUtil.replace(src.toString(), " ", "");
    return bankCard.substring(0, 4).concat(StrUtil.fillAfter("", '*', 11))
      .concat(bankCard.substring(15));
  }
}
