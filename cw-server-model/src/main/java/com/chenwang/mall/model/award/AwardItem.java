package com.chenwang.mall.model.award;

import com.chenwang.mall.common.base.IDEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name="tb_award_item")
@DynamicUpdate
public class AwardItem extends IDEntity {
  private String name;
  private Long price;
}
