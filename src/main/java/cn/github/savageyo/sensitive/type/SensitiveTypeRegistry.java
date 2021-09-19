package cn.github.savageyo.sensitive.type;

import cn.github.savageyo.sensitive.type.handler.AddressSensitiveHandler;
import cn.github.savageyo.sensitive.type.handler.BandCardSensitiveHandler;
import cn.github.savageyo.sensitive.type.handler.ChineseNameSensitiveHandler;
import cn.github.savageyo.sensitive.type.handler.DefaultSensitiveHandler;
import cn.github.savageyo.sensitive.type.handler.EmailSensitiveHandler;
import cn.github.savageyo.sensitive.type.handler.IDCardSensitiveHandler;
import cn.github.savageyo.sensitive.type.handler.MobileSensitiveHandler;
import cn.github.savageyo.sensitive.type.handler.NoneSensitiveHandler;
import cn.github.savageyo.sensitive.type.handler.UnionBankNoSensitiveHandler;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 脱敏处理注册表
 */
public class SensitiveTypeRegistry {

  private static final Map<SensitiveType, SensitiveTypeHandler> HANDLER_REGISTRY = new ConcurrentHashMap<>();

  static {
    HANDLER_REGISTRY.put(SensitiveType.ADDRESS, new AddressSensitiveHandler());
    HANDLER_REGISTRY.put(SensitiveType.BANK_CARD, new BandCardSensitiveHandler());
    HANDLER_REGISTRY.put(SensitiveType.CHINESE_NAME, new ChineseNameSensitiveHandler());
    HANDLER_REGISTRY.put(SensitiveType.DEFAULT, new DefaultSensitiveHandler());
    HANDLER_REGISTRY.put(SensitiveType.EMAIL, new EmailSensitiveHandler());
    HANDLER_REGISTRY.put(SensitiveType.ID_CARD, new IDCardSensitiveHandler());
    HANDLER_REGISTRY.put(SensitiveType.MOBILE_PHONE, new MobileSensitiveHandler());
    HANDLER_REGISTRY.put(SensitiveType.NONE, new NoneSensitiveHandler());
    HANDLER_REGISTRY.put(SensitiveType.UNION_BANK_NO, new UnionBankNoSensitiveHandler());
  }

  public static SensitiveTypeHandler get(SensitiveType sensitiveType) {
    return HANDLER_REGISTRY.get(sensitiveType);
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
