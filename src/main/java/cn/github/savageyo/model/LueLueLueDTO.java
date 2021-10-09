package cn.github.savageyo.model;

import cn.github.savageyo.sensitive.annotation.SensitiveBind;
import cn.github.savageyo.sensitive.annotation.SensitiveEncrypt;
import cn.github.savageyo.sensitive.type.SensitiveType;
import java.io.Serializable;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
public class LueLueLueDTO extends LueLueLue implements Serializable {

  private static final long serialVersionUID = -6828382569349002410L;

  @Transient
  @SensitiveBind(bindField = "idNo", sensitiveType = SensitiveType.DEFAULT)
  private String idNumber;
}
