package cn.github.savageyo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "cn.github.savageyo.mapper")
public class MybatisSensitiveApplication {

  public static void main(String[] args) {
    SpringApplication.run(MybatisSensitiveApplication.class, args);
  }

}
