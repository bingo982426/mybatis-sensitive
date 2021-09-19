package cn.github.savageyo.sensitive.type.handler;

import cn.github.savageyo.sensitive.type.SensitiveType;
import cn.github.savageyo.sensitive.type.SensitiveTypeHandler;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @Description 中文姓名脱敏
 * @Author Savage
 * @Date 2021/9/19
 */
public class ChineseNameSensitiveHandler implements SensitiveTypeHandler {

  private static final int HEAD_LENGTH = 1;

  @Override
  public SensitiveType getSensitiveType() {
    return SensitiveType.CHINESE_NAME;
  }

  @Override
  public String handle(Object src) {
    if (ObjectUtil.isEmpty(src) || !(src instanceof CharSequence)) {
      return null;
    }
    String chineseName = src.toString();
    return StrUtil.fillAfter(chineseName.substring(0, HEAD_LENGTH), SENSITIVE_SYMBOL_CHAR,
      chineseName.length());
  }
}
