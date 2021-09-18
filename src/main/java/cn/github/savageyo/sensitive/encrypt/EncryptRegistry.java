package cn.github.savageyo.sensitive.encrypt;


import cn.github.savageyo.sensitive.config.EncryptProperty;

/**
 * @Description 加密策略
 * @Author Savage
 * @Date 2021/9/15
 */
public class EncryptRegistry {

  public static Encrypt getEncryptType(String type, EncryptProperty encryptProperty) {
    if ("aes".equals(type)) {
      return new AesSupport(encryptProperty);
    } else if ("rsa".equals(type)) {
      return new RsaSupport(encryptProperty);
    } else if ("des".equals(type)) {
      return new DesSupport(encryptProperty);
    } else {
      return new AesSupport(encryptProperty);
    }
  }


}
