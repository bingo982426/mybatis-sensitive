package cn.github.savageyo.sensitive.type.handler;


import cn.github.savageyo.sensitive.type.SensitiveType;
import cn.github.savageyo.sensitive.type.SensitiveTypeHandler;
import cn.hutool.core.util.ObjectUtil;

/**
 * 默认脱敏处理类 只保留首位
 */
public class DefaultSensitiveHandler implements SensitiveTypeHandler {

  private static final int SIZE = 6;

  @Override
  public SensitiveType getSensitiveType() {
    return SensitiveType.DEFAULT;
  }


  @Override
  public String handle(Object src) {
    if (ObjectUtil.isEmpty(src) || !(src instanceof CharSequence)) {
      return null;
    }
    String value = src.toString();
    int totalLength = value.length();
    int halfLength = totalLength / 2;
    StringBuilder stringBuilder = new StringBuilder();
    //处理1-2位
    if (totalLength <= 2) {
      if (totalLength % 2 == 1) {
        return SENSITIVE_SYMBOL;
      }
      stringBuilder.append(SENSITIVE_SYMBOL);
      stringBuilder.append(value.charAt(totalLength - 1));
    } else {
      int condition = halfLength - 1;
      //处理三位的场景
      if (condition <= 0) {
        stringBuilder.append(value.charAt(0));
        stringBuilder.append(SENSITIVE_SYMBOL);
        stringBuilder.append(value.charAt(totalLength - 1));
        //处理8位以上
      } else if (condition >= SIZE / 2 && SIZE + 1 != totalLength) {
        int prefixLength = (totalLength - SIZE) / 2;
        stringBuilder.append(value, 0, prefixLength);
        for (int i = 0; i < SIZE; i++) {
          stringBuilder.append(SENSITIVE_SYMBOL);
        }
        stringBuilder.append(value, totalLength - (prefixLength + 1), totalLength);
        //保留首尾，其余补符号
      } else {
        int symbolLength = totalLength - 2;
        stringBuilder.append(value.charAt(0));
        for (int i = 0; i < symbolLength; i++) {
          stringBuilder.append(SENSITIVE_SYMBOL);
        }
        stringBuilder.append(value.charAt(totalLength - 1));
      }
    }
    return stringBuilder.toString();
  }
}
