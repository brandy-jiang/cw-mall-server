package com.chenwang.mall.model.payment;

import com.chenwang.mall.common.base.IDEntity;
import com.chenwang.mall.model.order.Order;
import com.chenwang.mall.model.user.Customer;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@Table(name="tb_user_finance_water_flow")
@DynamicUpdate
public class UserFinanceWaterFlow extends IDEntity {
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="order_id")
  private Order order;
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="deposit_id")
  private Deposit deposit;
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="withdraw_id")
  private Withdraw withdraw;

  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="customer_id")
  private Customer customer;
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="user_balance_id")
  private UserBalance userBalance;
  private Integer type;
  private Long delta;

  private Long afterUpdatedDelta;
}
