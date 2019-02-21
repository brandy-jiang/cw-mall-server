package com.chenwang.mall.wechat.common;


import lombok.Data;

/**
 * @author: Yu.Gan
 * @desc: 接口凭证
 * @date: 2017/12/9 15:14
 */
@Data
public class JSTicket {

  /**
   * 接口访问凭证
   */
  private String ticket;

  /**
   * 凭证有效期，单位：秒
   */
  private int expiresIn;

  /**
   * 创建时间，单位：秒 ，用于判断是否过期
   */
  private long createTime = System.currentTimeMillis();

  /**
   * 错误编码
   */
  private Integer errcode;

  /**
   * 错误消息
   */
  private String errmsg;


}



