package cn.github.savageyo.sensitive.type;

import cn.github.savageyo.sensitive.type.handler.AddressSensitiveHandler;
import cn.github.savageyo.sensitive.type.handler.DefaultSensitiveHandler;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 脱敏处理注册表
 */
public class SensitiveTypeRegistry {

  private static final Map<SensitiveType, SensitiveTypeHandler> HANDLER_REGISTRY = new ConcurrentHashMap<>();

  static {
    HANDLER_REGISTRY.put(SensitiveType.DEFAULT, new DefaultSensitiveHandler());
    HANDLER_REGISTRY.put(SensitiveType.ADDRESS, new AddressSensitiveHandler());
  }

  public static void put(SensitiveTypeHandler sensitiveTypeHandler) {
    HANDLER_REGISTRY.put(sensitiveTypeHandler.getSensitiveType(), sensitiveTypeHandler);
  }

  public static SensitiveTypeHandler get(SensitiveType sensitiveType) {

    SensitiveTypeHandler sensitiveTypeHandler = HANDLER_REGISTRY.get(sensitiveType);
    if (sensitiveTypeHandler == null) {
      throw new IllegalArgumentException(
        "none sensitiveTypeHandler be found!, type:" + sensitiveType.name());
    }
    return sensitiveTypeHandler;
  }

  /**
   * 是否已经是脱敏过的内容了
   *
   * @param src 原始数据
   * @return 是否已经脱敏了
   */
  public static boolean alreadyBeSensitive(Object src) {
    return src == null || src.toString().indexOf("*") > 0;
  }
}
