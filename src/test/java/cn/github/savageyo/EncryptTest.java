package cn.github.savageyo;

import cn.github.savageyo.mapper.WaLaLaMapper;
import cn.github.savageyo.model.WaLaLa;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@SpringBootTest
class EncryptTest {

  @Autowired
  WaLaLaMapper waLaLaMapper;

//  @Test
//  void genKey() {
//    byte[] aesEncoded = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
//    System.out.println("aesKey=" + HexUtil.encodeHexStr(aesEncoded));
//    byte[] desEncoded = SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue()).getEncoded();
//    System.out.println("desKey=" + HexUtil.encodeHexStr(desEncoded));
//    RSA rsa = new RSA();
//    String privateKeyBase64 = rsa.getPrivateKeyBase64();
//    System.out.println("rsa私钥=" + privateKeyBase64);
//    String publicKeyBase64 = rsa.getPublicKeyBase64();
//    System.out.println("rsa公钥=" + publicKeyBase64);
//  }

  @Test
  void insert() {
    WaLaLa waLaLa = new WaLaLa();
    waLaLa.setName("sam");
    waLaLa.setMobile("13828541328");
    waLaLa.setBankCard("6216614217283945859");
    waLaLa.setIdNo("110113199612131516");
    waLaLaMapper.insert(waLaLa);
    System.out.println(JSONUtil.toJsonStr(waLaLa));
    Assert.isFalse(waLaLa.getMobile().equals("13828541328"));
    Assert.isFalse(waLaLa.getBankCard().equals("6216614217283945859"));
    Assert.isFalse(waLaLa.getIdNo().equals("110113199612131516"));
  }

  @Test
  void insertSelective() {
    WaLaLa waLaLa = new WaLaLa();
    waLaLa.setName("sam");
    waLaLa.setMobile("13828541328");
    waLaLa.setBankCard("6216614217283945859");
    waLaLa.setIdNo("110113199612131516");
    waLaLaMapper.insertSelective(waLaLa);
    System.out.println(JSONUtil.toJsonStr(waLaLa));
    Assert.isFalse(waLaLa.getMobile().equals("13828541328"));
    Assert.isFalse(waLaLa.getBankCard().equals("6216614217283945859"));
    Assert.isFalse(waLaLa.getIdNo().equals("110113199612131516"));
  }

  @Test
  void insertList() {
    WaLaLa waLaLa = new WaLaLa();
    waLaLa.setName("sam");
    waLaLa.setMobile("13828541328");
    waLaLa.setBankCard("6216614217283945859");
    waLaLa.setIdNo("110113199612131516");
    WaLaLa waLaLa2 = new WaLaLa();
    waLaLa2.setName("tom");
    waLaLa2.setMobile("13827028884");
    waLaLa2.setBankCard("6216615283785976064");
    waLaLa2.setIdNo("11011319901016058X");
    List<WaLaLa> waLaLaList = new ArrayList<>();
    waLaLaList.add(waLaLa);
    waLaLaList.add(waLaLa2);
    waLaLaMapper.insertList(waLaLaList);
    System.out.println(JSONUtil.toJsonStr(waLaLaList));
    Assert.isFalse(waLaLa.getMobile().equals("13828541328"));
    Assert.isFalse(waLaLa.getBankCard().equals("6216614217283945859"));
    Assert.isFalse(waLaLa.getIdNo().equals("110113199612131516"));
    Assert.isFalse(waLaLa2.getMobile().equals("13827028884"));
    Assert.isFalse(waLaLa2.getBankCard().equals("6216615283785976064"));
    Assert.isFalse(waLaLa2.getIdNo().equals("11011319901016058X"));
  }

  @Test
  void updateByPrimaryKeySelective() {
    WaLaLa waLaLa = new WaLaLa();
    waLaLa.setId(1);
    waLaLa.setMobile("13333333333");
    waLaLaMapper.updateByPrimaryKeySelective(waLaLa);
    System.out.println(JSONUtil.toJsonStr(waLaLa));
    Assert.isFalse(waLaLa.getMobile().equals("13333333333"));
  }

  @Test
  void updateByPrimaryKey() {
    WaLaLa waLaLa = new WaLaLa();
    waLaLa.setId(2);
    waLaLa.setMobile("13333333333");
    waLaLaMapper.updateByPrimaryKey(waLaLa);
    System.out.println(JSONUtil.toJsonStr(waLaLa));
    Assert.isFalse(waLaLa.getMobile().equals("13333333333"));
  }

  @Test
  void updateByExample() {
    WaLaLa waLaLa = new WaLaLa();
    waLaLa.setMobile("13333333333");
    Example example = new Example(waLaLa.getClass());
    Criteria criteria = example.createCriteria();
    criteria.andEqualTo("id", 3);
    waLaLaMapper.updateByExample(waLaLa, example);
    System.out.println(JSONUtil.toJsonStr(waLaLa));
    Assert.isFalse(waLaLa.getMobile().equals("13333333333"));
  }

  @Test
  void updateByExampleSelective() {
    WaLaLa waLaLa = new WaLaLa();
    waLaLa.setMobile("13333333333");
    Example example = new Example(waLaLa.getClass());
    Criteria criteria = example.createCriteria();
    criteria.andEqualTo("id", 4);
    waLaLaMapper.updateByExampleSelective(waLaLa, example);
    System.out.println(JSONUtil.toJsonStr(waLaLa));
    Assert.isFalse(waLaLa.getMobile().equals("13333333333"));
  }

  @Test
  void select() {
    WaLaLa waLaLa = new WaLaLa();
    waLaLa.setId(1);
    List<WaLaLa> waLaLaList = waLaLaMapper.select(waLaLa);
    System.out.println(waLaLaList.get(0).getIdNo());
    System.out.println(JSONUtil.toJsonStr(waLaLaList));
    Assert.isTrue(waLaLaList.get(0).getIdNo().equals("110113199612131516"));
  }

  @Test
  void selectByPrimaryKey() {
    WaLaLa waLaLa = waLaLaMapper.selectByPrimaryKey(2);
    System.out.println(waLaLa.getMobile());
    Assert.isTrue(waLaLa.getMobile().equals("13333333333"));
  }

  @Test
  void selectByExample() {
    Example example = new Example(WaLaLa.class);
    Criteria criteria = example.createCriteria();
    criteria.andEqualTo("id", "1");
    List<WaLaLa> waLaLaList = waLaLaMapper.selectByExample(example);
    System.out.println(JSONUtil.toJsonStr(waLaLaList));
    Assert.isTrue(waLaLaList.get(0).getBankCard().equals("6216614217283945859"));
  }

  @Test
  void selectPage() {
    PageHelper.startPage(1, 10);
    List<WaLaLa> waLaLaList = waLaLaMapper.selectAll();
    System.out.println(JSONUtil.toJsonStr(waLaLaList));
    Assert.isTrue(waLaLaList.stream().filter(e -> e.getId() == 1)
      .anyMatch(e -> "6216614217283945859".equals(e.getBankCard())));
    PageInfo<WaLaLa> pageInfo = new PageInfo<>(waLaLaList);
    System.out.println(JSONUtil.toJsonStr(pageInfo));
  }
}
