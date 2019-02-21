package com.chenwang.mall.model.user;

import com.chenwang.mall.common.base.IDEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name="tb_wechat_user_info")
@DynamicUpdate
public class WechatUserInfo extends IDEntity {
  private String wxId;
  private String weixinId;
  private String wxUnionId;
//  @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
//  @JoinColumn(name="customer_id")
  //private Customer customer;
}
