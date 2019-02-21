package com.chenwang.mall.model;

import com.chenwang.mall.common.base.IDEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name="tb_attachment")
@DynamicUpdate
public class Attachment extends IDEntity {
  private String url;
  private String filename;
  private String size;
}
