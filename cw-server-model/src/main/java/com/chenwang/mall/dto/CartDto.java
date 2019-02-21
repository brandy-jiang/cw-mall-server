package com.chenwang.mall.dto;

import com.chenwang.mall.dto.backend.ProductDto;
import com.chenwang.mall.model.user.Address;
import lombok.Data;

import java.util.List;

@Data
public class CartDto {
  private List<Long> productIds;
  private List<ProductDto> productDtoList;
  private Address defaultAddress;
  int status;
}
