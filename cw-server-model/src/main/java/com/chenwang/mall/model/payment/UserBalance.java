package com.chenwang.mall.model.payment;

import com.chenwang.mall.common.base.IDEntity;
import com.chenwang.mall.model.user.Customer;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name="tb_user_balance")
@DynamicUpdate
public class UserBalance extends IDEntity {
  @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="customer_id")
  private Customer customer;
  private Long delta;
  private Long frozen;

  private BigDecimal bcl;

}
