package com.chenwang.mall.model.user;

import com.chenwang.mall.common.base.IDEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@Table(name="tb_red_envelope_gain_record")
@DynamicUpdate
public class RedEnvelopeGainRecord extends IDEntity {
  private Long bonus;
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="customer_id")
  private Customer owner;
}
