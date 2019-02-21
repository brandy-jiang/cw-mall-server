package com.chenwang.mall.wechat.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author: Yu.Gan
 * @desc: OAuth token
 * @date: 2017/12/9 14:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class OAuthAccessToken extends AccessToken {

  /**
   * oauth2.0 刷新token
   */
  private String oauthAccessToken;

  private String openid;

  private String scope;

  private String unionid;

}

