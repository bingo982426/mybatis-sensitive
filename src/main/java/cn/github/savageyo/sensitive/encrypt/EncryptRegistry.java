package cn.github.savageyo.sensitive.encrypt;

import cn.github.savageyo.sensitive.config.EncryptProperty;
import cn.github.savageyo.sensitive.encrypt.support.AesSupport;
import cn.github.savageyo.sensitive.encrypt.support.DesSupport;
import cn.github.savageyo.sensitive.encrypt.support.RsaSupport;

/**
 * @Description 加密策略
 * @Author Savage
 * @Date 2021/9/15
 */
public class EncryptRegistry {

  public static EncryptHandler getEncryptType(EncryptType type, EncryptProperty encryptProperty) {
    if (EncryptType.AES.equals(type)) {
      return new AesSupport(encryptProperty);
    } else if (EncryptType.RSA.equals(type)) {
      return new RsaSupport(encryptProperty);
    } else if (EncryptType.DES.equals(type)) {
      return new DesSupport(encryptProperty);
    } else {
      return new AesSupport(encryptProperty);
    }
  }


}
