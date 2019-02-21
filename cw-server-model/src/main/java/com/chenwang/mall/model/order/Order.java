package com.chenwang.mall.model.order;

import com.chenwang.mall.model.Attachment;
import com.chenwang.mall.common.base.IDEntity;
import com.chenwang.mall.model.payment.Deposit;
import com.chenwang.mall.model.product.Product;
import com.chenwang.mall.model.user.Customer;
import com.chenwang.mall.model.user.RedEnvelopeConsumeRecord;
import com.chenwang.mall.model.user.ScoreConsumeRecord;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="tb_order")
@DynamicUpdate
public class Order extends IDEntity {

  private Long realDelta;
  private Long delta;
  private Long score;
  private String address;
  private String linkTel;
  private String orderNo;
  private String receiver;
  private Long logisticsFee;
  private String logisticsName;
  private String logisticsNo;
  private String senderAddress;
  private String senderMobile;
  private String senderName;
  private String message;
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="red_envelope_consume_record_id")
  private RedEnvelopeConsumeRecord redEnvelopeConsumeRecord;
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="score_consume_record_id")
  private ScoreConsumeRecord scoreConsumeRecord;

  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch=FetchType.LAZY)
  @JoinColumn(name="order_id")
  @OrderBy("create_time desc")
  private List<OrderDetails> orderDetailsList;



  @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="deposit_id")
  private Deposit deposit;
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="customer_id")
  private Customer customer;
}
