package com.chenwang.mall.sms;

import org.springframework.stereotype.Service;

@Service
public class SMSService {
  public void sendCode(String mobile, String code, String sign){
    SMSHelper.sendCode(mobile, code, sign);
  }
}
