package com.chenwang.mall.model.user;

import com.chenwang.mall.common.base.IDEntity;
import com.chenwang.mall.model.device.Device;
import com.chenwang.mall.model.invite.InviteRelationship;
import com.chenwang.mall.model.payment.UserBalance;
import com.chenwang.mall.model.payment.UserFinanceWaterFlow;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="tb_customer")
@DynamicUpdate
public class Customer extends IDEntity {
  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch=FetchType.LAZY)
  @JoinColumn(name="customer_id")
  @OrderBy("create_time desc")
  private List<UserBankCard> userBankCardList;
  private String bankName;
  private String bankCardNo;
  @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="account_info_id")
  private AccountInfo accountInfo;
  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch=FetchType.LAZY)
  @JoinColumn(name="customer_id")
  @OrderBy("create_time desc")
  List<Favorite> favoriteList;
  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch=FetchType.LAZY)
  @JoinColumn(name="customer_id")
  @OrderBy("create_time desc")
  List<ScoreGainRecord> scoreList;
  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch=FetchType.LAZY)
  @JoinColumn(name="customer_id")
  @OrderBy("create_time desc")
  List<UserFinanceWaterFlow> userFinanceWaterFlowList;

  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch=FetchType.LAZY)
  @JoinColumn(name="customer_id")
  @OrderBy("create_time desc")
  List<InviteRelationship> inviteRelationshipList;
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="inviter_id")
  Customer inviter;

  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch=FetchType.LAZY)
  @JoinColumn(name="customer_id")
  @OrderBy("create_time desc")
  List<Device> deviceList;

  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch=FetchType.LAZY)
  @JoinColumn(name="customer_id")
  @OrderBy("create_time desc")
  List<CustomerSignInRecord> customerSignInRecordList;


  private Integer level;//普通会员:0 黄金会员:1 钻石会员 代理人 合伙人:5
  private Long score;

  @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="user_balance_id")
  private UserBalance userBalance;


}
