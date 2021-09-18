package cn.github.savageyo.sensitive.encrypt;

/**
 * 加解密接口
 */
public interface Encrypt {

  /**
   * 对原文进行加密
   *
   * @param originalText 原文
   * @return 返回加密后的密文
   * @throws RuntimeException 算法异常
   */
  String encrypt(String originalText);

  /**
   * 对密文进行解密
   *
   * @param cipherText 密文
   * @return 返回解密后的原文
   * @throws RuntimeException 算法异常
   */
  String decrypt(String cipherText);
}
