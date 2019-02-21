package com.chenwang.mall.baicai.controller;

import com.chenwang.mall.baicai.service.BannerService;
import com.chenwang.mall.baicai.service.NewsService;
import com.chenwang.mall.common.base.BaseController;
import com.chenwang.mall.common.base.BaseSpecification;
import com.chenwang.mall.dto.BannerDto;
import com.chenwang.mall.dto.NewsDto;
import com.chenwang.mall.dto.PageDto;
import com.chenwang.mall.dto.UserDto;
import com.chenwang.mall.common.base.IDEntity;
import com.chenwang.mall.dto.backend.NoticeDto;
import com.chenwang.mall.model.content.Banner;
import com.chenwang.mall.model.content.News;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/content")
@Slf4j
public class ContentController extends BaseController {
  @Autowired
  BannerService bannerService;
  @Autowired
  NewsService newsService;
  @RequestMapping(value="listBanner",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public List<BannerDto> listB(HttpServletRequest req){
    log.info(" request listBanner");
//    BannerDto user = (BannerDto) req.getSession().getAttribute("customer");
    Page<Banner> page = bannerService.findAll(new BaseSpecification<Banner>().where("status").eq(1).searchCustomized(), 0, 5, DEFAULT_SORT);
    List<BannerDto> result = BannerDto.convert(page.getContent());
    log.info("{}", result);
    return result;
  }

  @RequestMapping(value="listNews",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public List<NewsDto> listN(HttpServletRequest req){
    log.info(" request listBanner");
//    BannerDto user = (BannerDto) req.getSession().getAttribute("customer");
    Page<News> page = newsService.findAll(new BaseSpecification<News>().where("status").eq(1).searchCustomized(), 0, 5, DEFAULT_SORT);
    List<NewsDto> result = NewsDto.convert(page.getContent());
    log.info("{}", result);
    return result;
  }

  @RequestMapping(value="viewNotice",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public NoticeDto view(NoticeDto dto){
    log.info(" request view");
    try {
      return NoticeDto.convert(newsService.findOne(dto.getId()));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @RequestMapping(value="listNotice",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public List<NoticeDto> listB(NoticeDto dto, HttpServletRequest req){
    log.info(" request list {}", dto);
//    BannerDto user = (BannerDto) req.getSession().getAttribute("customer");
    Page<News> page = newsService.findAll(newsService.where("status").eq(1)
        .and("title").like(dto.getTitle())
        .searchCustomized(), (dto.getPage() - 1) * PAGE_SIZE, PAGE_SIZE, DEFAULT_SORT);
    List<NoticeDto> result = NoticeDto.convert(page.getContent());



    log.info("{}",result);
    return result;
  }
}
