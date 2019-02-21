package com.chenwang.mall.dto.backend;

import com.chenwang.mall.common.utils.DateUtils;
import com.chenwang.mall.model.user.Customer;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CustomerDto {
  private String nickname;
  private Integer level;
  private String inviteFrom;//username
  private String mobile;
  private String createTime;
  private Long score;
  private Long id;

  private int page;
  private String username;
  private String password;
  private String rePassword;
  private String invCode;

  private String bankName;
  private String bankCardNo;

  public static CustomerDto convert(Customer customer){
    CustomerDto customerDto = new CustomerDto();
    customerDto.setId(customer.getId());
    customerDto.setCreateTime(DateUtils.format(customer.getCreateTime()));
    if(customer.getInviter() != null) {
      customerDto.setInviteFrom(customer.getInviter().getAccountInfo().getUsername());
      customerDto.setInvCode(customer.getInviter().getAccountInfo().getUsername());
    }
    else
      customerDto.setInviteFrom("无");
    customerDto.setLevel(customer.getLevel());
    customerDto.setMobile(customer.getAccountInfo().getMobile());
    customerDto.setNickname(customer.getAccountInfo().getNickname());
    customerDto.setScore(customer.getScore());
    customerDto.setBankCardNo(customer.getBankCardNo());
    customerDto.setBankName(customer.getBankName());
    return customerDto;
  }

  public static List<CustomerDto> convert(List<Customer> bannerList){
    List<CustomerDto> result = new ArrayList<>();
    bannerList.forEach(ea ->{
      CustomerDto dto = convert(ea);
      result.add(dto);
    });
    return result;
  }
}
