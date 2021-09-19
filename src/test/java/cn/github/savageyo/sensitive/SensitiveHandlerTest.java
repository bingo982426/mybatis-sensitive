package cn.github.savageyo.sensitive;

import cn.github.savageyo.sensitive.type.handler.AddressSensitiveHandler;
import cn.github.savageyo.sensitive.type.handler.BandCardSensitiveHandler;
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
    String result = mobilePhoneSensitiveHandler.handle("18233583070");
    System.out.println(result);
  }

  @Test
  public void test2() {
    DefaultSensitiveHandler defaultSensitiveHandler = new DefaultSensitiveHandler();
    String result = defaultSensitiveHandler.handle("阿沁无颗粒木栅");
    System.out.println(result);
  }

  @Test
  public void test3() {
    EmailSensitiveHandler emailSensitiveHandler = new EmailSensitiveHandler();
    String result = emailSensitiveHandler.handle("342342324@zen.com");
    System.out.println(result);
  }

  @Test
  public void test4() {
    AddressSensitiveHandler addressSensitiveHandler = new AddressSensitiveHandler();
    String result = addressSensitiveHandler.handle("北京市朝阳区呼家楼街道");
    System.out.println(result);
  }

  @Test
  public void test5() {
    BandCardSensitiveHandler bandCardSensitiveHandler = new BandCardSensitiveHandler();
    String result = bandCardSensitiveHandler.handle("6216618562697313228");
    System.out.println(result);
  }

  @Test
  public void test6() {
    IDCardSensitiveHandler idCardSensitiveHandler = new IDCardSensitiveHandler();
    String result = idCardSensitiveHandler.handle("11011319960930042X");
    System.out.println(result);
  }

  @Test
  public void test7() {
    UnionBankNoSensitiveHandler unionBankNoSensitiveHandler = new UnionBankNoSensitiveHandler();
    String result = unionBankNoSensitiveHandler.handle("307241038009");
    System.out.println(result);
  }
}
