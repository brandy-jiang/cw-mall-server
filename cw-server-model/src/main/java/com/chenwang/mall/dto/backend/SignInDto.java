package com.chenwang.mall.dto.backend;

import com.chenwang.mall.common.utils.DateUtils;
import com.chenwang.mall.model.user.CustomerSignInRecord;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SignInDto {
  private String nickname;
  private String createTime;
  private Integer page;
  public static SignInDto convert(CustomerSignInRecord customerSignInRecord){
    SignInDto signInDto = new SignInDto();
    signInDto.setCreateTime(DateUtils.format(customerSignInRecord.getCreateTime()));
    signInDto.setNickname(customerSignInRecord.getCustomer().getAccountInfo().getNickname());
    return signInDto;
  }

  public static List<SignInDto> convert(List<CustomerSignInRecord> bannerList){
    List<SignInDto> result = new ArrayList<>();
    bannerList.forEach(ea ->{
      SignInDto dto = convert(ea);
      result.add(dto);
    });
    return result;
  }
}
