package com.chenwang.mall.model.user;

import com.chenwang.mall.common.base.IDEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name="tb_customer_sign_in_record")
@DynamicUpdate
public class CustomerSignInRecord extends IDEntity {
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="customer_id")
  private Customer customer;
  private BigDecimal signInPoint;
}
