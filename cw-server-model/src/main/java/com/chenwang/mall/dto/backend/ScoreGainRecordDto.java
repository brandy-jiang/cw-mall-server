package com.chenwang.mall.dto.backend;

import com.chenwang.mall.common.utils.DateUtils;
import com.chenwang.mall.model.user.ScoreGainRecord;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScoreGainRecordDto {
  private String username;
  private String orderNo;
  private String createTime;
  private String type;
  private Integer intType;
  private Long score;

  private Long id;

  private Integer page;

  public static ScoreGainRecordDto convert(ScoreGainRecord banner){
    ScoreGainRecordDto bannerDto = new ScoreGainRecordDto();
    bannerDto.setId(banner.getId());
    bannerDto.setOrderNo(banner.getOrderNo());
    bannerDto.setScore(banner.getGained());
    switch (banner.getType()){
      case 0:
        bannerDto.setType("直推奖");
        break;
      case 1:
        bannerDto.setType("团队奖");
        break;
      case 2:
        bannerDto.setType("购买商品");
        break;
    }
    bannerDto.setIntType(banner.getType());
    bannerDto.setUsername(banner.getUsername());
    bannerDto.setCreateTime(DateUtils.format(banner.getCreateTime()));

    return bannerDto;
  }

  public static List<ScoreGainRecordDto> convert(List<ScoreGainRecord> bannerList){
    List<ScoreGainRecordDto> result = new ArrayList<>();
    bannerList.forEach(ea ->{
      ScoreGainRecordDto dto = convert(ea);
      result.add(dto);
    });
    return result;
  }
}
