package com.chenwang.mall.dto.backend;

import com.chenwang.mall.common.utils.DateUtils;
import com.chenwang.mall.model.content.News;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NoticeDto {
  private Long id;
  private int page;
  private String title;
  private String content;
  private String url;
  private String createBy;
  private String createTime;
  private String author;
  public static NoticeDto convert(News banner){
    NoticeDto bannerDto = new NoticeDto();
    bannerDto.setId(banner.getId());
    bannerDto.setContent(banner.getContent());
    bannerDto.setTitle(banner.getTitle());
    bannerDto.setAuthor(banner.getAuthor());
    bannerDto.setCreateTime(DateUtils.format(banner.getCreateTime()));

    return bannerDto;
  }

  public static List<NoticeDto> convert(List<News> bannerList){
    List<NoticeDto> result = new ArrayList<>();
    bannerList.forEach(ea ->{
      NoticeDto dto = convert(ea);
      result.add(dto);
    });
    return result;
  }

}
