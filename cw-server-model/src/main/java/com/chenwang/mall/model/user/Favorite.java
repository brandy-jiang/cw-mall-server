package com.chenwang.mall.model.user;

import com.chenwang.mall.common.base.IDEntity;
import com.chenwang.mall.model.product.Product;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@Table(name="tb_favorite")
@DynamicUpdate
public class Favorite extends IDEntity {
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="product_id")
  private Product product;
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="customer_id")
  private Customer customer;
}
