package cn.github.savageyo.mybatis.expand;

import java.util.List;
import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

/**
 * 批量update
 *
 * @param <T> 不能为空
 */
@RegisterMapper
public interface UpdateBatchByPrimaryKeySelectiveMapper<T> {

  /**
   * 根据Example条件批量更新实体`record`包含的不是null的属性值
   */
  @UpdateProvider(type = BatchExampleProvider.class, method = "dynamicSQL")
  int updateBatchByPrimaryKeySelective(List<? extends T> recordList);

}