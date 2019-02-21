package com.chenwang.mall.model.content;

import com.chenwang.mall.common.base.IDEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name="tb_hot_keywords")
@DynamicUpdate
public class HotKeywords extends IDEntity {
  private String keywords;
}
