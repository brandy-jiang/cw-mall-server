package com.chenwang.mall.baicai.service;

import com.chenwang.mall.baicai.dao.BannerRepository;
import com.chenwang.mall.common.base.BaseService;
import com.chenwang.mall.dto.BannerDto;
import com.chenwang.mall.model.content.Banner;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class BannerService extends BaseService<Banner, BannerRepository> {
  @Transactional
  public void addBanner(BannerDto dto) {
    if(dto.getId() != null){
      updateNews(dto);
      return;
    }
    Banner banner = new Banner();
    banner.setTitle(dto.getTitle());
    banner.setCreateTime(new Date());
    banner.setStatus(1);
    banner.setUrl(dto.getUrl());
    save(banner);
  }

  private void updateNews(BannerDto dto) {
    Banner banner = findOne(dto.getId());
    banner.setTitle(dto.getTitle());
    banner.setUrl(dto.getUrl());
    banner.setUpdateTime(new Date());
  }
}
