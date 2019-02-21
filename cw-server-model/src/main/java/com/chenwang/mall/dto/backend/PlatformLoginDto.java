package com.chenwang.mall.dto.backend;

import lombok.Data;

import java.util.Date;

@Data
public class PlatformLoginDto {
  private String username;
  private String password;
  private Long id;
  private Date loginTime;
}
