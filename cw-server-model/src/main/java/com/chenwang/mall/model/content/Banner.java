package com.chenwang.mall.model.content;

import com.chenwang.mall.model.Attachment;
import com.chenwang.mall.common.base.IDEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@Table(name="tb_banner")
@DynamicUpdate
public class Banner extends IDEntity {
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="pic_id")
  private Attachment pic;
  private String position;
  private String title;
  private String url;
}
