package com.chenwang.mall.model.user;

import com.chenwang.mall.common.base.IDEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="tb_account_info")
@DynamicUpdate
public class AccountInfo extends IDEntity {
  @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="customer_id")
  private Customer customer;
  private String username;
  private String password;
  private String payPassword;
  private Date lastLoginTime;
  @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="wechat_user_info_id")
  private WechatUserInfo wechatUserInfo;
  private String headImgUrl;
  private String nickname;
  private Integer gender;
  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch=FetchType.LAZY)
  @JoinColumn(name="account_info_id")
  @OrderBy("create_time desc")
  private List<Address> addressList;

  private String mobile;
}
