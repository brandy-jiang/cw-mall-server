package com.chenwang.mall.baicai.service;

import com.chenwang.mall.baicai.dao.AwardItemRepository;
import com.chenwang.mall.common.base.BaseService;
import com.chenwang.mall.dto.AwardItemDto;
import com.chenwang.mall.model.award.AwardItem;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class AwardItemService extends BaseService<AwardItem, AwardItemRepository> {
  @Transactional
  public void addAwardItem(AwardItemDto dto) {
    if(dto.getId() != null){
      updateAwardItem(dto);
      return;
    }
    AwardItem awardItem = new AwardItem();
    awardItem.setPrice(new BigDecimal(dto.getPrice() * 100).longValue());
    awardItem.setName(dto.getName());
    awardItem.setCreateTime(new Date());
    awardItem.setStatus(1);
    save(awardItem);
  }
  private void updateAwardItem(AwardItemDto dto){
    AwardItem awardItem = findOne(dto.getId());
    awardItem.setName(dto.getName());
    awardItem.setPrice(new BigDecimal(dto.getPrice() * 100).longValue());
    awardItem.setUpdateTime(new Date());
  }
}
