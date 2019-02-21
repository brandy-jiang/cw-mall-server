package com.chenwang.mall.model.platform;

import com.chenwang.mall.common.base.IDEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name="tb_platform_user")
@DynamicUpdate
public class PlatformUser extends IDEntity {
  private String username;
  private String password;
  private Date lastLoginTime;
}
