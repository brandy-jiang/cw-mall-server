package com.chenwang.mall.model.user;

import com.chenwang.mall.common.base.IDEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name="tb_address")
@DynamicUpdate
public class Address extends IDEntity {
  private String address;
  private String receiver;
  private String linkTel;
  private Integer isDefault;
}
