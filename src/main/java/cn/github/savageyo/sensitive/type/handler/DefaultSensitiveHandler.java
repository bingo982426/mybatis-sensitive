package cn.github.savageyo.sensitive.type.handler;


import cn.github.savageyo.sensitive.type.SensitiveType;
import cn.github.savageyo.sensitive.type.SensitiveTypeHandler;
import cn.hutool.core.util.StrUtil;

/**
 * 默认脱敏处理类 只保留首位
 */
public class DefaultSensitiveHandler implements SensitiveTypeHandler {

  @Override
  public SensitiveType getSensitiveType() {
    return SensitiveType.DEFAULT;
  }

  @Override
  public String handle(Object src) {
    if (StrUtil.isEmptyIfStr(src)) {
      return null;
    }
    String str = src.toString();
    return StrUtil.fillAfter(str.substring(0, 1), '*', str.length());
  }
}
