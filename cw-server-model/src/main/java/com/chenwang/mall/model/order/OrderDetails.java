package com.chenwang.mall.model.order;

import com.chenwang.mall.common.base.IDEntity;
import com.chenwang.mall.model.product.Product;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@Table(name="tb_order_details")
@DynamicUpdate
public class OrderDetails extends IDEntity {
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="product_id")
  private Product product;
  private String productName;
  private Long realDelta;
  private Long delta;
  private Long score;
  private Long num;
}
