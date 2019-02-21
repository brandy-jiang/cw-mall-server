package com.chenwang.mall.model.user;

import com.chenwang.mall.common.base.IDEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@Table(name="tb_score_gain_record")
@DynamicUpdate
public class ScoreGainRecord extends IDEntity {
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="customer_id")
  private Customer customer;
  private String username;
  private Long gained;//can < 0
  private String orderNo;
  private Integer type;//直推 团队 购买商品

}
