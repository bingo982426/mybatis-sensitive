package cn.github.savageyo.sensitive.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description 加密配置类
 * @Author Savage
 * @Date 2021/9/15
 */
@Data
@Component
@ConfigurationProperties(prefix = "sensitive.encrypt.mybatis")
public class EncryptProperty {

  private String aesPassword;
  private String desPassword;
  private String rsaPrivateKey;
  private String rsaPublicKey;
}
