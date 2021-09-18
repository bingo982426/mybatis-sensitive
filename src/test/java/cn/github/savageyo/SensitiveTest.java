package cn.github.savageyo;

import cn.github.savageyo.mapper.LueLueLueMapper;
import cn.github.savageyo.model.LueLueLue;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import java.util.List;
import javax.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SensitiveTest {

  @Autowired
  LueLueLueMapper lueLueLueMapper;

  @PostConstruct
  void init() {
    lueLueLueMapper.delete(new LueLueLue());
    LueLueLue lueLueLue = new LueLueLue();
    lueLueLue.setName("john");
    lueLueLue.setIdNo("110113199805210472");
    lueLueLue.setMobile("13859607169");
    lueLueLue.setBankCard("6216613241837891071");
    lueLueLueMapper.insertSelective(lueLueLue);
  }

  @Test
  void select() {
    List<LueLueLue> lueLueLueList = lueLueLueMapper.selectAll();
    System.out.println(JSONUtil.toJsonStr(lueLueLueList));
    Assert.isTrue(lueLueLueList.get(0).getMobile().contains("*"));
  }

  @Test
  void selectCustom() {
    LueLueLue lueLueLue = lueLueLueMapper.selectIdNoById(2);
    System.out.println(JSONUtil.toJsonStr(lueLueLue));
    Assert.isTrue(lueLueLue.getIdNo().contains("*"));
  }
}
