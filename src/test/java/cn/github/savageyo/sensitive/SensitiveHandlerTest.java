package cn.github.savageyo.sensitive;

import cn.github.savageyo.sensitive.type.handler.AddressSensitiveHandler;
import cn.github.savageyo.sensitive.type.handler.BandCardSensitiveHandler;
import cn.github.savageyo.sensitive.type.handler.ChineseNameSensitiveHandler;
import cn.github.savageyo.sensitive.type.handler.DefaultSensitiveHandler;
import cn.github.savageyo.sensitive.type.handler.EmailSensitiveHandler;
import cn.github.savageyo.sensitive.type.handler.IDCardSensitiveHandler;
import cn.github.savageyo.sensitive.type.handler.MobileSensitiveHandler;
import cn.github.savageyo.sensitive.type.handler.UnionBankNoSensitiveHandler;
import org.junit.Test;

public class SensitiveHandlerTest {

  @Test
  public void test() {
    MobileSensitiveHandler mobilePhoneSensitiveHandler = new MobileSensitiveHandler();
    System.out.println(mobilePhoneSensitiveHandler.handle("18233583070"));
  }

  @Test
  public void test2() {
    DefaultSensitiveHandler defaultSensitiveHandler = new DefaultSensitiveHandler();
    System.out.println(defaultSensitiveHandler.handle("苹果"));
    System.out.println(defaultSensitiveHandler.handle("编译器"));
    System.out.println(defaultSensitiveHandler.handle("海内存知己"));
    System.out.println(defaultSensitiveHandler.handle("mybatis拦截器加密解密及脱敏"));
  }

  @Test
  public void test3() {
    EmailSensitiveHandler emailSensitiveHandler = new EmailSensitiveHandler();
    System.out.println(emailSensitiveHandler.handle("342342324@zen.com"));
  }

  @Test
  public void test4() {
    AddressSensitiveHandler addressSensitiveHandler = new AddressSensitiveHandler();
    System.out.println(addressSensitiveHandler.handle("北京市朝阳区"));
    System.out.println(addressSensitiveHandler.handle("北京市朝阳区呼家楼街道"));
    System.out.println(addressSensitiveHandler.handle("北京市朝阳区东三环中路25号住总大厦"));
  }

  @Test
  public void test5() {
    BandCardSensitiveHandler bandCardSensitiveHandler = new BandCardSensitiveHandler();
    System.out.println(bandCardSensitiveHandler.handle("6216618562697313228"));
  }

  @Test
  public void test6() {
    IDCardSensitiveHandler idCardSensitiveHandler = new IDCardSensitiveHandler();
    System.out.println(idCardSensitiveHandler.handle("310112850409522"));
    System.out.println(idCardSensitiveHandler.handle("11011319960930042X"));
  }

  @Test
  public void test7() {
    UnionBankNoSensitiveHandler unionBankNoSensitiveHandler = new UnionBankNoSensitiveHandler();
    System.out.println(unionBankNoSensitiveHandler.handle("307241038009"));
  }

  @Test
  public void test8() {
    ChineseNameSensitiveHandler chineseNameSensitiveHandler = new ChineseNameSensitiveHandler();
    System.out.println(chineseNameSensitiveHandler.handle("张辽"));
  }
}
