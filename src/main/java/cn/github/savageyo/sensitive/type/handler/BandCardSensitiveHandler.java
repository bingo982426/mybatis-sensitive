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

  private static final int HEAD_LENGTH = 4;
  private static final int TAIL_LENGTH = 4;

  @Override
  public SensitiveType getSensitiveType() {
    return SensitiveType.BANK_CARD;
  }

  @Override
  public String handle(Object src) {
    if (!needHandler(src)) {
      return null;
    }
    String bankCard = StrUtil.replace(src.toString(), " ", "");
    int length = bankCard.length();
    return StrUtil.padAfter(bankCard.substring(0, HEAD_LENGTH), length - TAIL_LENGTH,
      SENSITIVE_SYMBOL_CHAR).concat(bankCard.substring(length - TAIL_LENGTH));
  }
}
