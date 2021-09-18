package cn.github.savageyo.mybatis;

import cn.github.savageyo.mybatis.expand.BatchMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>, BatchMapper<T> {

}
