package com.chenwang.mall.dto.backend;

import com.chenwang.mall.common.utils.DateUtils;
import com.chenwang.mall.model.device.HealthRecord;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HealthRecordDto {
  private String owner;
  private String deviceNo;
  private String recordDate;
  private String heartRate;
  private String bloodPressure;
  private String bloodOxygen;
  private String todayWalkSteps;
  private String calorie;
  private Long id;

  private Integer page;

  public static HealthRecordDto convert(HealthRecord banner){
    HealthRecordDto bannerDto = new HealthRecordDto();
    bannerDto.setId(banner.getId());
    bannerDto.setBloodOxygen(banner.getBloodOxygen().toString());
    bannerDto.setBloodPressure(banner.getBloodPressure().toString());
    bannerDto.setCalorie(banner.getCalorie().toString());
    bannerDto.setRecordDate(DateUtils.format(banner.getRecordDate()));
    bannerDto.setDeviceNo(banner.getDevice().getDeviceNo());
    bannerDto.setHeartRate(banner.getHeartRate().toString());
    bannerDto.setId(banner.getId());
    bannerDto.setTodayWalkSteps(banner.getTodayWalkSteps().toString());
    bannerDto.setOwner(banner.getCustomer().getAccountInfo().getNickname());
    bannerDto.setRecordDate(DateUtils.format(banner.getRecordDate()));
    return bannerDto;
  }

  public static List<HealthRecordDto> convert(List<HealthRecord> bannerList){
    List<HealthRecordDto> result = new ArrayList<>();
    bannerList.forEach(ea ->{
      HealthRecordDto dto = convert(ea);
      result.add(dto);
    });
    return result;
  }
}
