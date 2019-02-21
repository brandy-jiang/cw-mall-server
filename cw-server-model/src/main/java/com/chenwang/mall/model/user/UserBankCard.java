package com.chenwang.mall.model.user;

import com.chenwang.mall.common.base.IDEntity;
import com.chenwang.mall.model.bank.Bank;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@Table(name="tb_user_bank_card")
@DynamicUpdate
public class UserBankCard extends IDEntity {
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="customer_id")
  private Customer customer;
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="bank_id")
  private Bank bank;
  private String cardNo;
  private String owner;
  private String openingBank;
}
