package com.chenwang.mall.baicai.controller.backend;

import com.chenwang.mall.baicai.service.DeviceConfigService;
import com.chenwang.mall.baicai.service.DeviceService;
import com.chenwang.mall.baicai.service.HealthRecordService;
import com.chenwang.mall.common.base.BaseController;
import com.chenwang.mall.dto.PageDto;
import com.chenwang.mall.dto.backend.DeviceConfigDto;
import com.chenwang.mall.dto.backend.DeviceDto;
import com.chenwang.mall.dto.backend.HealthRecordDto;
import com.chenwang.mall.model.device.Device;
import com.chenwang.mall.model.device.HealthRecord;
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
@RequestMapping("/api/backend/device")
@Slf4j
public class DeviceController extends BaseController {
  @Autowired
  DeviceService deviceService;
  @Autowired
  DeviceConfigService deviceConfigService;
  @Autowired
  HealthRecordService healthRecordService;
  @RequestMapping(value="/list",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public PageDto<DeviceDto> listB(DeviceDto dto, HttpServletRequest req){
    log.info(" request list {}", dto);
//    BannerDto user = (BannerDto) req.getSession().getAttribute("customer");
    Page<Device> page = deviceService.findAll(deviceService.where("status").eq(dto.getStatus())
        .and("deviceNo","customer.accountInfo.nickname").like(dto.getDeviceNo(), dto.getOwner())
        .searchCustomized(), (dto.getPage() - 1) * PAGE_SIZE, PAGE_SIZE, DEFAULT_SORT);
    List<DeviceDto> result = DeviceDto.convert(page.getContent());
    PageDto pd = new PageDto();
    pd.setCount(page.getTotalElements());
    pd.setData(result);
    log.info("{}", pd);
    return pd;
  }

  @RequestMapping(value="/health/list",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public PageDto<HealthRecordDto> listB(HealthRecordDto dto, HttpServletRequest req){
    log.info(" request list {}", dto);
//    BannerDto user = (BannerDto) req.getSession().getAttribute("customer");
    Page<HealthRecord> page = healthRecordService.findAll(healthRecordService.where("status").eq(1)
        .and("device.deviceNo","customer.accountInfo.nickname").like(dto.getDeviceNo(), dto.getOwner())
        .searchCustomized(), (dto.getPage() - 1) * PAGE_SIZE, PAGE_SIZE, DEFAULT_SORT);
    List<HealthRecordDto> result = HealthRecordDto.convert(page.getContent());
    PageDto pd = new PageDto();
    pd.setCount(page.getTotalElements());
    pd.setData(result);
    log.info("{}", pd);
    return pd;
  }

  @RequestMapping(value="/config/add",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String add(DeviceConfigDto dto){
    log.info(" request add");
    try {
      deviceConfigService.updateDeviceConfig(dto);
      return "{\"msg\":\"success\"}";
      //resp.sendRedirect(java.net.URLEncoder.encode("user_list.html", "utf-8"));
    } catch (Exception e) {
      e.printStackTrace();
      return "{\"msg\":\"添加用户失败\"}";
    }

  }
  @RequestMapping(value="/config/view",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public DeviceConfigDto view(DeviceConfigDto dto){
    log.info(" request view");
    try {
      return DeviceConfigDto.convert(deviceConfigService.findOne(dto.getId()));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}
