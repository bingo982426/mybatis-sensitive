package cn.github.savageyo.sensitive.type.handler;


import cn.github.savageyo.sensitive.type.SensitiveType;
import cn.github.savageyo.sensitive.type.SensitiveTypeHandler;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 邮件信息脱敏处理类
 * 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示
 * 例子:g**@163.com
 */
public class EmailSensitiveHandler implements SensitiveTypeHandler {

  private static final int HEAD_LENGTH = 1;
  private static final char EMAIL_SYMBOL = '@';

  @Override
  public SensitiveType getSensitiveType() {
    return SensitiveType.EMAIL;
  }

  @Override
  public String handle(Object src) {
    if (ObjectUtil.isEmpty(src) || !(src instanceof CharSequence)) {
      return null;
    }
    String email = src.toString();
    int index = StrUtil.indexOf(email, EMAIL_SYMBOL);
    if (index <= 1) {
      return email;
    } else {
      return StrUtil.concat(true,
        StrUtil.fillAfter(email.substring(0, HEAD_LENGTH), SENSITIVE_SYMBOL_CHAR, index),
        email.substring(index));
    }
  }
}
