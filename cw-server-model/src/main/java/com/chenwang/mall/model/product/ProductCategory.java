package com.chenwang.mall.model.product;

import com.chenwang.mall.common.base.IDEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@Table(name="tb_product_category")
@DynamicUpdate
public class ProductCategory extends IDEntity {
  private String name;
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="parent_id")
  private ProductCategory parent;
}
