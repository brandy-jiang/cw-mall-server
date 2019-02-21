package com.chenwang.mall.baicai.controller.backend;

import com.chenwang.mall.baicai.service.AwardItemService;
import com.chenwang.mall.common.base.BaseController;
import com.chenwang.mall.dto.AwardItemDto;
import com.chenwang.mall.dto.PageDto;

import com.chenwang.mall.model.award.AwardItem;
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
@RequestMapping("/api/backend/award")
@Slf4j
public class AwardItemController extends BaseController {
  @Autowired
  AwardItemService awardItemService;
  @RequestMapping(value="/item/list",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public PageDto<AwardItemDto> listB(AwardItemDto dto, HttpServletRequest req){
    log.info(" request list {}", dto);
//    BannerDto user = (BannerDto) req.getSession().getAttribute("customer");
    Page<AwardItem> page = awardItemService.findAll(awardItemService.where("status").eq(1)
        .and("name").like(dto.getName())
        .searchCustomized(), (dto.getPage() - 1) * PAGE_SIZE, PAGE_SIZE, DEFAULT_SORT);
    List<AwardItemDto> result = AwardItemDto.convert(page.getContent());
    PageDto pd = new PageDto();
    pd.setCount(page.getTotalElements());
    pd.setData(result);
    log.info("{}", pd);
    return pd;
  }

  @RequestMapping(value="/item/add",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String add(AwardItemDto dto){
    log.info(" request add");
    try {
      awardItemService.addAwardItem(dto);
      return "{\"msg\":\"success\"}";
      //resp.sendRedirect(java.net.URLEncoder.encode("user_list.html", "utf-8"));
    } catch (Exception e) {
      e.printStackTrace();
      return "{\"msg\":\"添加用户失败\"}";
    }

  }
  @RequestMapping(value="/item/view",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public AwardItemDto view(AwardItemDto dto){
    log.info(" request view");
    try {
      return AwardItemDto.convert(awardItemService.findOne(dto.getId()));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  @RequestMapping(value="/item/del",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String del(AwardItemDto dto){
    log.info(" request del");
    try {
      awardItemService.deleteById(dto.getId());
      return "{\"msg\":\"success\"}";
    } catch (Exception e) {
      e.printStackTrace();
      return "{\"msg\":\"error\"}";
    }
  }
}
