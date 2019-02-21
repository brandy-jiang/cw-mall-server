package com.chenwang.mall.model.content;

import com.chenwang.mall.common.base.IDEntity;
import com.chenwang.mall.model.platform.PlatformUser;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@Table(name="tb_news")
@DynamicUpdate
public class News extends IDEntity {
  private String title;
  private String content;
  private Integer sort;
  private String author;
}
