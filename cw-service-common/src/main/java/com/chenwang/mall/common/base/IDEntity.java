package com.chenwang.mall.common.base;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
public class IDEntity<T extends Serializable> implements Serializable {
  @Id
  @GeneratedValue(
      strategy= GenerationType.AUTO,
      generator="native"
  )
  @GenericGenerator(
      name = "native",
      strategy = "native"
  )
  private Long id;
  private Date createTime;
  private Date updateTime;
  private Integer status;

}
