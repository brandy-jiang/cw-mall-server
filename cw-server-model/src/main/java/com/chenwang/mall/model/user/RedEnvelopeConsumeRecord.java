package com.chenwang.mall.model.user;

import com.chenwang.mall.common.base.IDEntity;
import com.chenwang.mall.model.order.Order;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@Table(name="tb_red_envelope_consume_record")
@DynamicUpdate
public class RedEnvelopeConsumeRecord extends IDEntity {
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="order_id")
  private Order order;
  private Long consumed;
}
