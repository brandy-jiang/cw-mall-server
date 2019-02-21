package com.chenwang.mall.model.user;

import com.chenwang.mall.model.Attachment;
import com.chenwang.mall.common.base.IDEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="tb_account_info")
@DynamicUpdate
public class Comment extends IDEntity {
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch= FetchType.LAZY)
  @JoinColumn(name="customer_id")
  private Customer customer;
  private String content;
  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch=FetchType.LAZY)
  @JoinColumn(name="target_id")
  @OrderBy("create_time desc")
  private List<Attachment> pics;
}
