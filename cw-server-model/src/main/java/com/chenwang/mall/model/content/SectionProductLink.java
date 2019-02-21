package com.chenwang.mall.model.content;

import com.chenwang.mall.common.base.IDEntity;
import com.chenwang.mall.model.product.Product;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@Table(name="tb_section_product_link")
@DynamicUpdate
public class SectionProductLink extends IDEntity {
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="section_id")
  private Section section;
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="product_id")
  private Product product;
  private Integer sort;
}
