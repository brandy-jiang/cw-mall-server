package com.chenwang.mall.dto;

import com.chenwang.mall.model.payment.UserBalance;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserBalanceDto {
  private String score;
  private String profit;
  private String bcl;
  private Long deviceNum;
  private String balance;
  private Long id;
}
