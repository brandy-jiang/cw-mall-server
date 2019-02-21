package com.chenwang.mall.dto;

import com.chenwang.mall.common.utils.DateUtils;
import com.chenwang.mall.model.content.Banner;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BannerDto {
  private Long id;
  private String img;
  private String url;
  private Integer page;
  private String title;
  private String createTime;

  public static BannerDto convert(Banner banner){
    BannerDto bannerDto = new BannerDto();
    bannerDto.setId(banner.getId());
    if(banner.getPic() != null)
      bannerDto.setImg(banner.getPic().getUrl());
    bannerDto.setTitle(banner.getTitle());
    bannerDto.setCreateTime(DateUtils.format(banner.getCreateTime()));
    bannerDto.setUrl(banner.getUrl());
    return bannerDto;
  }

  public static List<BannerDto> convert(List<Banner> bannerList){
    List<BannerDto> result = new ArrayList<>();
    bannerList.forEach(ea ->{
      BannerDto dto = convert(ea);
      result.add(dto);
    });
    return result;
  }
}
