package cn.github.savageyo.sensitive.encrypt;

import cn.github.savageyo.sensitive.config.EncryptProperty;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import java.nio.charset.StandardCharsets;

/**
 * @Description rsa加密
 * @Author Savage
 * @Date 2021/9/15
 */
public class RsaSupport implements Encrypt {

  private final RSA rsa;

  public RsaSupport(EncryptProperty encryptProperty) {
    this.rsa = getRSA(encryptProperty.getRsaPrivateKey(), encryptProperty.getRsaPublicKey());
  }

  private static RSA getRSA(final String privateKey, final String publicKey) {
    return SecureUtil.rsa(privateKey, publicKey);
  }

  @Override
  public String encrypt(String value) {
    return rsa.encryptBcd(value, KeyType.PublicKey, StandardCharsets.UTF_8);
  }

  @Override
  public String decrypt(String value) {
    if (StrUtil.isEmpty(value)) {
      return "";
    }
    return rsa.decryptStr(value, KeyType.PrivateKey, StandardCharsets.UTF_8);
  }
}
