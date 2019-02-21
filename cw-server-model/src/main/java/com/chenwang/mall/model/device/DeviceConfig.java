package com.chenwang.mall.model.device;

import com.chenwang.mall.common.base.IDEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name="tb_device_config")
@DynamicUpdate
public class DeviceConfig extends IDEntity {
  private Long minePoolSize;
  private Long minePerSec;
}
