package com.chenwang.mall.wechat.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Yu.Gan
 * @desc:
 * @date: 2018/1/12 10:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsApiReturn {

  private String appId;
  private String timestamp;
  private String nonceStr;
  private String signature;

}
