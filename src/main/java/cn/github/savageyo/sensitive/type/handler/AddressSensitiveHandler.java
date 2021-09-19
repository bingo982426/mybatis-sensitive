package cn.github.savageyo.sensitive.type.handler;

import cn.github.savageyo.sensitive.type.SensitiveType;
import cn.github.savageyo.sensitive.type.SensitiveTypeHandler;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 收货地址脱敏处理类
 * 地址只显示到地区，不显示详细地址；我们要对个人信息增强保护
 * 例子：北京市海淀区****
 */
public class AddressSensitiveHandler implements SensitiveTypeHandler {

  private static final int HEAD_LENGTH = 6;
  private static final int SYMBOL_LENGTH = 10;

  @Override
  public SensitiveType getSensitiveType() {
    return SensitiveType.ADDRESS;
  }

  @Override
  public String handle(Object src) {
    if (ObjectUtil.isEmpty(src) || !(src instanceof CharSequence)) {
      return null;
    }
    String address = src.toString();
    int length = StrUtil.length(address);
    if (length > SYMBOL_LENGTH + HEAD_LENGTH) {
      return StrUtil.padAfter(address.substring(0, HEAD_LENGTH), HEAD_LENGTH + SYMBOL_LENGTH,
        SENSITIVE_SYMBOL);
    }
    if (length <= HEAD_LENGTH) {
      return address;
    } else {
      return StrUtil.padAfter(address.substring(0, HEAD_LENGTH), length, SENSITIVE_SYMBOL);
    }
  }
}
