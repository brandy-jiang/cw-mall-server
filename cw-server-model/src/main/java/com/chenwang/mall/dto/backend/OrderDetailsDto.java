package com.chenwang.mall.dto.backend;

import com.chenwang.mall.common.utils.DateUtils;
import com.chenwang.mall.model.order.OrderDetails;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDetailsDto {
  private String productName;
  private Long delta;
  private Long num;
  private Long sumDelta;
  private Long id;
  private Long score;
  private Integer type;
  private String shopType;

  public static OrderDetailsDto convert(OrderDetails banner){
    OrderDetailsDto bannerDto = new OrderDetailsDto();
    bannerDto.setId(banner.getId());
    bannerDto.setProductName(banner.getProductName());

    bannerDto.setDelta(banner.getDelta());
    bannerDto.setScore(banner.getScore());


    bannerDto.setNum(banner.getNum());
    bannerDto.setSumDelta(banner.getNum() * banner.getDelta());

    return bannerDto;
  }

  public static List<OrderDetailsDto> convert(List<OrderDetails> bannerList){
    List<OrderDetailsDto> result = new ArrayList<>();
    bannerList.forEach(ea ->{
      OrderDetailsDto dto = convert(ea);
      result.add(dto);
    });
    return result;
  }
}
