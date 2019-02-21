package com.chenwang.mall.model.payment;

import com.chenwang.mall.common.base.IDEntity;
import com.chenwang.mall.model.order.Order;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@Table(name="tb_deposit")
@DynamicUpdate
public class Deposit extends IDEntity {
  @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="order_id")
  private Order order;
  private Long delta;
  private Long realDelta;
  private String callbackMsg;
  private String orderNo;
  private String channelOrderNo;
  private String retCode;
}
