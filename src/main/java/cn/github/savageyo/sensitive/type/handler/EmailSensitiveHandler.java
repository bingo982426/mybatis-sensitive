package cn.github.savageyo.sensitive.type.handler;


import cn.github.savageyo.sensitive.type.SensitiveType;
import cn.github.savageyo.sensitive.type.SensitiveTypeHandler;
import cn.hutool.core.util.StrUtil;

/**
 * 邮件信息脱敏处理类
 * 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示
 * 例子:g**@163.com
 */
public class EmailSensitiveHandler implements SensitiveTypeHandler {

  @Override
  public SensitiveType getSensitiveType() {
    return SensitiveType.EMAIL;
  }

  @Override
  public String handle(Object src) {
    if (src == null) {
      return null;
    }
    String email = src.toString();
    int index = StrUtil.indexOf(email, '@');
    if (index <= 1) {
      return email;
    } else {
      return StrUtil.concat(true, StrUtil.fillAfter(email.substring(0, 1), '*', index),
        email.substring(index));
    }
  }
}
