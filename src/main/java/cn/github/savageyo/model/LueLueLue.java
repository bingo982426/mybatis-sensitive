package cn.github.savageyo.model;

import cn.github.savageyo.sensitive.annotation.SensitiveEncrypt;
import cn.github.savageyo.sensitive.annotation.SensitiveField;
import cn.github.savageyo.sensitive.type.SensitiveType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 实体类
 * @Author Savage
 * @Date 2021/9/18
 */
@Data
@SensitiveEncrypt
@NoArgsConstructor
@AllArgsConstructor
public class LueLueLue implements Serializable {

  private static final long serialVersionUID = -1076465765126891656L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
  private Integer id;
  @Column(name = "name")
  private String name;
  @Column(name = "id_no")
  @SensitiveField(SensitiveType.DEFAUL)
  private String idNo;
  @SensitiveField(SensitiveType.DEFAUL)
  @Column(name = "mobile")
  private String mobile;
  @Column(name = "bank_card")
  private String bankCard;
}
