package com.chenwang.mall.dto.backend;

import com.chenwang.mall.common.utils.DateUtils;
import com.chenwang.mall.model.device.Device;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DeviceDto {
  private String deviceNo;
  private String bindTime;
  private String owner;
  private String status;
  private Long mine;
  private Long id;

  private Integer page;
  public static DeviceDto convert(Device banner){
    DeviceDto bannerDto = new DeviceDto();
    bannerDto.setId(banner.getId());
    bannerDto.setDeviceNo(banner.getDeviceNo());
    bannerDto.setMine(banner.getMine());
    bannerDto.setOwner(banner.getCustomer().getAccountInfo().getNickname());
    bannerDto.setBindTime(DateUtils.format(banner.getBindTime()));
    if(banner.getStatus() == 1){
      bannerDto.setStatus("开启");
    }else{
      bannerDto.setStatus("关闭");
    }
    return bannerDto;
  }

  public static List<DeviceDto> convert(List<Device> bannerList){
    List<DeviceDto> result = new ArrayList<>();
    bannerList.forEach(ea ->{
      DeviceDto dto = convert(ea);
      result.add(dto);
    });
    return result;
  }
}
