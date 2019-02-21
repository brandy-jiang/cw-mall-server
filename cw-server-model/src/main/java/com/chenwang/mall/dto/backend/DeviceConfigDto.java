package com.chenwang.mall.dto.backend;

import com.chenwang.mall.model.device.DeviceConfig;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DeviceConfigDto {
  private Long id;
  private Long minePoolSize;
  private Long minePerSec;

  public static DeviceConfigDto convert(DeviceConfig banner){
    DeviceConfigDto bannerDto = new DeviceConfigDto();
    bannerDto.setId(banner.getId());
    bannerDto.setMinePoolSize(banner.getMinePoolSize());
    bannerDto.setMinePerSec(banner.getMinePerSec());

    return bannerDto;
  }

  public static List<DeviceConfigDto> convert(List<DeviceConfig> bannerList){
    List<DeviceConfigDto> result = new ArrayList<>();
    bannerList.forEach(ea ->{
      DeviceConfigDto dto = convert(ea);
      result.add(dto);
    });
    return result;
  }
}
