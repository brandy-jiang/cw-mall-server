package com.chenwang.mall.dto.backend;

import com.chenwang.mall.common.utils.DateUtils;
import com.chenwang.mall.model.product.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDto{
  private String name;
  private String createTime;
  private Integer type;
  private String shopType;
  private Long stock;
  private String status;
  private Integer intStatus;
  private Long id;
  private Integer page;
  private Double price;
  private Long score;
  private Long scoreGained;
  private String description;
  private Double marketPrice;
  private Long num;

  private Integer isSelected;

  public static ProductDto convert(Product banner){
    ProductDto bannerDto = new ProductDto();
    bannerDto.setId(banner.getId());
    bannerDto.setName(banner.getTitle());
    bannerDto.setStock(banner.getStock());
    bannerDto.setPrice(banner.getPrice() / 100.);
    bannerDto.setMarketPrice(banner.getMarketPrice() / 100.);
    bannerDto.setScore(banner.getScore());
    bannerDto.setCreateTime(DateUtils.format(banner.getCreateTime()));
    bannerDto.setShopType(banner.getShopType() == 1?"上架":"下架");
    bannerDto.setType(banner.getShopType());
    bannerDto.setDescription(banner.getDescription());
    bannerDto.setIntStatus(banner.getStatus());
    bannerDto.setScoreGained(banner.getScoreGained());
    if(banner.getStatus() == 1){
      bannerDto.setStatus("上架");
    }else{
      bannerDto.setStatus("下架");
    }
    return bannerDto;
  }

  public static List<ProductDto> convert(List<Product> bannerList){
    List<ProductDto> result = new ArrayList<>();
    bannerList.forEach(ea ->{
      ProductDto dto = convert(ea);
      result.add(dto);
    });
    return result;
  }
}
