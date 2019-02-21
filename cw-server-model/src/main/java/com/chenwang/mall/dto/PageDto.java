package com.chenwang.mall.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDto<T> {
  private List<T> data;
  private Long count;
}
