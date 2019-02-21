package com.chenwang.mall.model.product;

import com.chenwang.mall.common.base.IDEntity;
import com.chenwang.mall.model.Attachment;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="tb_product")
@DynamicUpdate
public class Product extends IDEntity {
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="product_category_id")
  private ProductCategory productCategory;
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="brand_id")
  private Brand brand;
  private String image;
  private Long marketPrice;//cent
  private Long price;//cent
  private Long promotionPrice;//cent
  private Long stock;
  private String title;
  private String description;
  private Integer sort;
  private Long score;
  private Integer shopType;//商城 || 积分
  private Integer isRecommended;
  private Long scoreGained;

  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="list_img_id")
  private Attachment listImg;
  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch=FetchType.LAZY)
  @JoinColumn(name="target_id")
  @OrderBy("create_time desc")
  private List<Attachment> pics;

}
