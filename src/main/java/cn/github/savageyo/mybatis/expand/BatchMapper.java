package cn.github.savageyo.mybatis.expand;


import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface BatchMapper<T> extends UpdateBatchByPrimaryKeySelectiveMapper<T> {

}