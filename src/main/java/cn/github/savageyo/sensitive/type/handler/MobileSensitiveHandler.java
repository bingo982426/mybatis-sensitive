package cn.github.savageyo.sensitive.type.handler;


import cn.github.savageyo.sensitive.type.SensitiveType;
import cn.github.savageyo.sensitive.type.SensitiveTypeHandler;
import cn.hutool.core.util.StrUtil;

/**
 * 手机号脱敏处理类
 * 18233583070 脱敏后: 182****3030
 */
public class MobileSensitiveHandler implements SensitiveTypeHandler {

  @Override
  public SensitiveType getSensitiveType() {
    return SensitiveType.MOBILE_PHONE;
  }

  @Override
  public String handle(Object src) {
    if (StrUtil.isEmptyIfStr(src)) {
      return null;
    }
    String mobile = src.toString();
    int length = mobile.length();
    return StrUtil.padAfter(mobile.substring(0, 3), length - 4, "*")
      .concat(mobile.substring(length - 4));
  }
}
