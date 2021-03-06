package com.chenwang.mall.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WxUserInfoDto {

  /**
   * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
   */
  private Integer subscribe;

  /**
   * 用户的标识，对当前公众号唯一
   */
  private String openid;

  /**
   * 用户的昵称
   */
  private String nickname;

  /**
   * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
   */
  private Integer sex;

  /**
   * 用户的语言，简体中文为zh_CN
   */
  private String language;

  /**
   * 用户所在城市
   */
  private String city;

  /**
   * 用户所在省份
   */
  private String province;

  /**
   * 用户所在国家
   */
  private String country;

  /**
   * 用户头像
   */
  private String headimgurl;

  /**
   * 公众号运营者对粉丝的备注
   */
  private String remark;

  /**
   * 用户所在的分组ID
   */
  private Integer groupid;

  /**
   * 用户关注时间，为时间戳
   */
  private Long subscribe_time;

  /**
   * 用户被打上的标签ID列表
   */
  private Object tagid_list;

  /**
   * 用户特权信息
   */
  private Object privilege;

  /**
   * 用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
   */
  private String unionid;

}
