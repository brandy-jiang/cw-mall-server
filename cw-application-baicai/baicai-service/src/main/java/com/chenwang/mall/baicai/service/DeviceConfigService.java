package com.chenwang.mall.baicai.service;

import com.chenwang.mall.baicai.dao.DeviceConfigRepository;
import com.chenwang.mall.common.base.BaseService;
import com.chenwang.mall.dto.backend.DeviceConfigDto;
import com.chenwang.mall.model.device.DeviceConfig;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class DeviceConfigService extends BaseService<DeviceConfig, DeviceConfigRepository> {
  @Transactional
  public void updateDeviceConfig(DeviceConfigDto dto) {
    if(dto.getId() == null){
      DeviceConfig deviceConfig = new DeviceConfig();
      deviceConfig.setMinePerSec(dto.getMinePerSec());
      deviceConfig.setMinePoolSize(dto.getMinePoolSize());
      deviceConfig.setCreateTime(new Date());
      deviceConfig.setStatus(1);
      save(deviceConfig);
    }else {
      DeviceConfig deviceConfig = findOne(dto.getId());
      deviceConfig.setMinePoolSize(dto.getMinePoolSize());
      deviceConfig.setMinePerSec(dto.getMinePerSec());
    }
  }
}
