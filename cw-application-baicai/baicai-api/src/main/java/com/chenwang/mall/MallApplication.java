package com.chenwang.mall;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("com.chenwang.mall")
public class MallApplication {
  public static void main(String[] args) throws Exception {
    org.springframework.boot.SpringApplication.run(MallApplication.class, args);
  }
}