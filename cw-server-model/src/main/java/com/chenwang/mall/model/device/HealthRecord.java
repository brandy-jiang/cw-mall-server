package com.chenwang.mall.model.device;

import com.chenwang.mall.common.base.IDEntity;
import com.chenwang.mall.model.user.Customer;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name="tb_health_record")
@DynamicUpdate
public class HealthRecord extends IDEntity {
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="customer_id")
  private Customer customer;
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="device_id")
  private Device device;
  private Date recordDate;
  private BigDecimal heartRate;
  private BigDecimal bloodPressure;
  private BigDecimal bloodOxygen;
  private Integer todayWalkSteps;
  private BigDecimal calorie;

}
