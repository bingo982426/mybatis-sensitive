package cn.github.savageyo.sensitive.type.handler;

import cn.github.savageyo.sensitive.type.SensitiveType;
import cn.github.savageyo.sensitive.type.SensitiveTypeHandler;
import cn.hutool.core.util.StrUtil;

/**
 * 收货地址脱敏处理类
 * 地址只显示到地区，不显示详细地址；我们要对个人信息增强保护
 * 例子：北京市海淀区****
 */
public class AddressSensitiveHandler implements SensitiveTypeHandler {

  private static final int RIGHT = 10;
  private static final int LEFT = 6;

  @Override
  public SensitiveType getSensitiveType() {
    return SensitiveType.ADDRESS;
  }

  @Override
  public String handle(Object src) {
    if (StrUtil.isEmptyIfStr(src)) {
      return null;
    }
    String address = src.toString();
    int length = StrUtil.length(address);
    if (length > RIGHT + LEFT) {
      return StrUtil.padAfter(address.substring(0, length - RIGHT), length, "*");
    }
    if (length <= LEFT) {
      return address;
    } else {
      return StrUtil.padAfter(address.substring(0, LEFT + 1), length, "*");
    }
  }
}
