package cn.github.savageyo.mapper;

import cn.github.savageyo.model.LueLueLue;
import cn.github.savageyo.mybatis.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description mapper
 * @Author Savage
 * @Date 2021/9/18
 */
@Mapper
public interface LueLueLueMapper extends BaseMapper<LueLueLue> {

  LueLueLue selectIdNoById(@Param("id") Integer id);
}
