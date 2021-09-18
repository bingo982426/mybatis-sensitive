package cn.github.savageyo.sensitive.encrypt;

import cn.github.savageyo.sensitive.config.EncryptProperty;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

/**
 * 数据脱敏用到的AES加解密类
 */
public class AesSupport implements Encrypt {

  private final AES aes;

  public AesSupport(EncryptProperty encryptProperty) {
    this.aes = getAes(encryptProperty.getAesPassword());
  }

  private static AES getAes(final String password) {
    return SecureUtil.aes(HexUtil.decodeHex(password));
  }

  @Override
  public String encrypt(String value) {
    return aes.encryptHex(value);
  }

  @Override
  public String decrypt(String value) {
    if (StrUtil.isEmpty(value)) {
      return "";
    }
    return aes.decryptStr(value);
  }

//  public static void main(String[] args) {
//    byte[] aesEncoded = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
//    System.out.println(HexUtil.encodeHexStr(aesEncoded));
//    byte[] desEncoded = SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue()).getEncoded();
//    System.out.println(HexUtil.encodeHexStr(desEncoded));
//    RSA rsa = new RSA();
//    String privateKeyBase64 = rsa.getPrivateKeyBase64();
//    String privateKeyHex = HexUtil.encodeHexStr(rsa.getPrivateKey().getEncoded());
//    String publicKeyBase64 = rsa.getPublicKeyBase64();
//    String publicKeyHex = HexUtil.encodeHexStr(rsa.getPublicKey().getEncoded());
//    System.out.println(privateKeyHex);
//    System.out.println(privateKeyBase64);
//    System.out.println(publicKeyHex);
//    System.out.println(publicKeyBase64);
//    System.out.println(privateKeyBase64.equals(privateKeyHex));
//    System.out.println(publicKeyBase64.equals(publicKeyHex));
//  }
}
