package com.chenwang.mall.baicai.controller.backend;

import com.chenwang.mall.baicai.service.BannerService;
import com.chenwang.mall.common.base.BaseController;
import com.chenwang.mall.dto.BannerDto;
import com.chenwang.mall.dto.PageDto;
import com.chenwang.mall.model.content.Banner;
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
@RequestMapping("/api/backend/banner")
@Slf4j
public class BannerController extends BaseController {
  @Autowired
  BannerService bannerService;
  @RequestMapping(value="list",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public PageDto<BannerDto> listB(BannerDto dto, HttpServletRequest req){
    log.info(" request list {}", dto);
//    BannerDto user = (BannerDto) req.getSession().getAttribute("customer");
    Page<Banner> page = bannerService.findAll(bannerService.where("status").eq(1)
        .searchCustomized(), (dto.getPage() - 1) * PAGE_SIZE, PAGE_SIZE, DEFAULT_SORT);
    List<BannerDto> result = BannerDto.convert(page.getContent());
    PageDto pd = new PageDto();
    pd.setCount(page.getTotalElements());
    pd.setData(result);
    log.info("{}", pd);
    return pd;
  }

  @RequestMapping(value="add",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String add(BannerDto dto){
    log.info(" request add");
    try {
      bannerService.addBanner(dto);
      return "{\"msg\":\"success\"}";
      //resp.sendRedirect(java.net.URLEncoder.encode("user_list.html", "utf-8"));
    } catch (Exception e) {
      e.printStackTrace();
      return "{\"msg\":\"添加用户失败\"}";
    }

  }
  @RequestMapping(value="view",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public BannerDto view(BannerDto dto){
    log.info(" request view");
    try {
      return BannerDto.convert(bannerService.findOne(dto.getId()));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  @RequestMapping(value="del",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String del(BannerDto dto){
    log.info(" request del");
    try {
      bannerService.deleteById(dto.getId());
      return "{\"msg\":\"success\"}";
    } catch (Exception e) {
      e.printStackTrace();
      return "{\"msg\":\"error\"}";
    }
  }
}
