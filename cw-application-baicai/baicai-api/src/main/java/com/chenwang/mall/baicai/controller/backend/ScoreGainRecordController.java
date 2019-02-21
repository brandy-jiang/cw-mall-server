package com.chenwang.mall.baicai.controller.backend;

import com.chenwang.mall.baicai.service.NewsService;
import com.chenwang.mall.baicai.service.ScoreGainRecordService;
import com.chenwang.mall.common.base.BaseController;
import com.chenwang.mall.dto.PageDto;
import com.chenwang.mall.dto.backend.ScoreGainRecordDto;

import com.chenwang.mall.model.user.ScoreGainRecord;
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
@RequestMapping("/api/backend/score")
@Slf4j
public class ScoreGainRecordController extends BaseController {
  @Autowired
  ScoreGainRecordService scoreGainRecordService;
  @RequestMapping(value="list",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public PageDto<ScoreGainRecordDto> listB(ScoreGainRecordDto dto, HttpServletRequest req){
    log.info(" request list {}", dto);
//    BannerDto user = (BannerDto) req.getSession().getAttribute("customer");
    Page<ScoreGainRecord> page = scoreGainRecordService.findAll(scoreGainRecordService.where("status","type").eq(1,dto.getIntType())
        .and("username").like(dto.getUsername())
        .searchCustomized(), (dto.getPage() - 1) * PAGE_SIZE, PAGE_SIZE, DEFAULT_SORT);
    List<ScoreGainRecordDto> result = ScoreGainRecordDto.convert(page.getContent());
    PageDto pd = new PageDto();
    pd.setCount(page.getTotalElements());
    pd.setData(result);
    log.info("{}", pd);
    return pd;
  }
}
