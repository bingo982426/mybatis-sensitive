package cn.github.savageyo.sensitive.type.handler;


import cn.github.savageyo.sensitive.type.SensitiveType;
import cn.github.savageyo.sensitive.type.SensitiveTypeHandler;

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
    if (!needHandler(src)) {
      return null;
    }
    return src.toString();
  }
}
