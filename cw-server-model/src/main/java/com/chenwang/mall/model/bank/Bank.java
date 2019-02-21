package com.chenwang.mall.model.bank;

import com.chenwang.mall.common.base.IDEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name="tb_bank")
@DynamicUpdate
public class Bank extends IDEntity {
  private String bankNo;
  private String name;
  private String code;
}
