package cn.github.savageyo.mybatis.expand;

import java.util.Set;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.provider.ExampleProvider;

/**
 * 批量更新的SqlProvider
 */

public class BatchExampleProvider extends ExampleProvider {

  public BatchExampleProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
    super(mapperClass, mapperHelper);
  }


  /**
   * 拼update sql, 使用case when方式，id为主键
   */
  public String updateBatchByPrimaryKeySelective(MappedStatement ms) {
    final Class<?> entityClass = getEntityClass(ms);
    //开始拼sql
    StringBuilder sql = new StringBuilder();
    sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
    sql.append("<trim prefix=\"set\" suffixOverrides=\",\">");

    //获取全部列
    Set<EntityColumn> entityColumnSet = EntityHelper.getColumns(entityClass);
    for (EntityColumn column : entityColumnSet) {
      if (!column.isId() && column.isUpdatable()) {
        sql.append("  <trim prefix=\"" + column.getColumn() + " =case\" suffix=\"end,\">");
        sql.append("    <foreach collection=\"list\" item=\"i\" index=\"index\">");
        sql.append("      <if test=\"i." + column.getEntityField().getName() + "!=null\">");
        sql.append("         when id=#{i.id} then #{i." + column.getEntityField().getName() + "}");
        sql.append("      </if>");
        sql.append("    </foreach>");
        sql.append("  </trim>");
      }
    }

    sql.append("</trim>");
    sql.append("WHERE");
    sql.append(" id IN ");
    sql.append("<trim prefix=\"(\" suffix=\")\">");
    sql.append("<foreach collection=\"list\" separator=\", \" item=\"i\" index=\"index\" >");
    sql.append("#{i.id}");
    sql.append("</foreach>");
    sql.append("</trim>");

    return sql.toString();
  }
}