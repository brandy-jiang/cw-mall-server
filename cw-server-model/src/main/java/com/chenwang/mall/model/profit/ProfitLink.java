package com.chenwang.mall.model.profit;

import com.chenwang.mall.common.base.IDEntity;
import com.chenwang.mall.model.user.Customer;
import lombok.Data;

@Data
public class ProfitLink extends IDEntity {
  private Customer fromUser;
  private Customer toUser;
  private Long point;
}
