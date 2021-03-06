package com.chenwang.mall.model.content;

import com.chenwang.mall.common.base.IDEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name="tb_section")
@DynamicUpdate
public class Section extends IDEntity {
  private String name;
}
