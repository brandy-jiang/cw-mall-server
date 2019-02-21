package com.chenwang.mall.model.award;

import com.chenwang.mall.common.base.IDEntity;
import com.chenwang.mall.model.user.Customer;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@Table(name="tb_award_record")
@DynamicUpdate
public class AwardRecord extends IDEntity {
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="award_item_id")
  private AwardItem awardItem;
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="customer_id")
  private Customer customer;
  private String nickname;
  private String itemName;
  private Long price;
}
