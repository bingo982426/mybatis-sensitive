package cn.github.savageyo.sensitive.encrypt;

import cn.github.savageyo.sensitive.config.EncryptProperty;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;

/**
 * @Description des加密策略
 * @Author Savage
 * @Date 2021/9/15
 */
public class DesSupport implements Encrypt {

  private final DES des;

  public DesSupport(EncryptProperty encryptProperty) {
    this.des = getDes(encryptProperty.getDesPassword());
  }

  private static DES getDes(final String password) {
    return SecureUtil.des(HexUtil.decodeHex(password));
  }

  @Override
  public String encrypt(String value) {
    return des.encryptHex(value);
  }

  @Override
  public String decrypt(String value) {
    if (StrUtil.isEmpty(value)) {
      return "";
    }
    return des.decryptStr(value);
  }

}
