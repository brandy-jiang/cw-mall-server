package com.chenwang.mall.dto;

import com.chenwang.mall.model.award.AwardItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AwardItemDto {
  private String name;
  private Double price;
  private Long id;
  private Integer page;
  public static AwardItemDto convert(AwardItem banner){
    AwardItemDto bannerDto = new AwardItemDto();
    bannerDto.setId(banner.getId());
    bannerDto.setName(banner.getName());
    bannerDto.setPrice(banner.getPrice() / 100.);
    return bannerDto;
  }

  public static List<AwardItemDto> convert(List<AwardItem> bannerList){
    List<AwardItemDto> result = new ArrayList<>();
    bannerList.forEach(ea ->{
      AwardItemDto dto = convert(ea);
      result.add(dto);
    });
    return result;
  }
}
