package cn.github.savageyo.sensitive.type.handler;


import cn.github.savageyo.sensitive.type.SensitiveType;
import cn.github.savageyo.sensitive.type.SensitiveTypeHandler;
import cn.hutool.core.util.ObjectUtil;

/**
 * 不脱敏
 */
public class NoneSensitiveHandler implements SensitiveTypeHandler {

  @Override
  public SensitiveType getSensitiveType() {
    return SensitiveType.NONE;
  }

  @Override
  public String handle(Object src) {
    if (ObjectUtil.isEmpty(src) || !(src instanceof CharSequence)) {
      return null;
    }
    return src.toString();
  }
}
