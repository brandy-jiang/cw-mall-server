package com.chenwang.mall.dto;

import com.chenwang.mall.model.user.Customer;
import lombok.Data;

@Data
public class UserDto {
  private Long id;
  private String headImgUrl;
  private String nickname;
  private Integer sex;
  private String bankName;
  private String bankCardNo;
  private String payPassword;

  private String rePayPassword;

  private String mobile;
  private String username;

  private String password;
  private String newPassword;



  public static UserDto convert(Customer user){
    UserDto userDto = new UserDto();
    userDto.setBankCardNo(user.getBankCardNo());
    userDto.setBankName(user.getBankName());
    userDto.setHeadImgUrl(user.getAccountInfo().getHeadImgUrl());
    userDto.setId(user.getId());
    userDto.setNickname(user.getAccountInfo().getNickname());
    userDto.setSex(user.getAccountInfo().getGender());
    userDto.setUsername(user.getAccountInfo().getUsername());
    userDto.setMobile(user.getAccountInfo().getMobile());
    return userDto;
  }


}
