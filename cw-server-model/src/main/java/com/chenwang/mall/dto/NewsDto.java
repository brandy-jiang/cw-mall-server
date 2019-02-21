package com.chenwang.mall.dto;

import com.chenwang.mall.model.content.News;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewsDto {
  private String title;
  private String content;
  private String url;
  private Long id;


  public static NewsDto convert(News banner){
    NewsDto bannerDto = new NewsDto();
    bannerDto.setId(banner.getId());
    bannerDto.setContent(banner.getContent());
    bannerDto.setTitle(banner.getTitle());
    return bannerDto;
  }

  public static List<NewsDto> convert(List<News> bannerList){
    List<NewsDto> result = new ArrayList<>();
    bannerList.forEach(ea ->{
      NewsDto dto = convert(ea);
      result.add(dto);
    });
    return result;
  }
}
