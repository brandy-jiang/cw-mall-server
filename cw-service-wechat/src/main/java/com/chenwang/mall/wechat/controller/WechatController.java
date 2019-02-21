package com.chenwang.mall.wechat.controller;

import com.chenwang.mall.baicai.service.UserService;
import com.chenwang.mall.dto.UserDto;
import com.chenwang.mall.dto.WxUserInfoDto;
import com.chenwang.mall.model.user.Customer;
import com.chenwang.mall.wechat.common.OAuthAccessToken;
import com.chenwang.mall.wechat.common.SignUtil;
import com.chenwang.mall.wechat.config.WechatConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/wechat")
@Slf4j
public class WechatController {
  @Autowired
  WechatConfig wechatConfig;
  @Autowired
  UserService userService;
  @RequestMapping(value="authCallback",method = {RequestMethod.GET}, produces = MediaType.APPLICATION_XML_VALUE)
  public String test(HttpServletRequest req){
    log.info("aaa -> {}", req.getParameter("echostr"));
    log.info("aaa -> {}", req.getQueryString());
    return checkUrl(req);

  }

  /**
   * oAuthResp
   */
  @RequestMapping(value = "/oAuthResp", method = RequestMethod.GET)
  void getOAuthAccessToken(@RequestParam("state") String state,
                           @RequestParam("code") String code, HttpServletRequest req, HttpServletResponse resp){
    log.info("query -> {}", code);
    OAuthAccessToken token = SignUtil.getOAuthAccessToken(wechatConfig.getAppId(), wechatConfig.getAppsecret(), code);
    log.info("accessToken -> {} openId -> {}", token.getAccessToken(), token.getOpenid());
    WxUserInfoDto dto = SignUtil.getWeiXinAppUserInfo(token.getAccessToken(), token.getOpenid());
    Customer customer = userService.autoRegister(dto);
    UserDto user = new UserDto();
    user.setId(customer.getId());
    user.setNickname(customer.getAccountInfo().getNickname());
    user.setHeadImgUrl(customer.getAccountInfo().getHeadImgUrl());
    user.setSex(customer.getAccountInfo().getGender());
    req.getSession().setAttribute("customer", user);
    log.info(" dto -> {}", dto);
    try {
      resp.sendRedirect("/index.html");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



  private String checkUrl(HttpServletRequest request) {
    //微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp，nonce参数
    String signature = request.getParameter("signature");
    //时间戳
    String timestamp = request.getParameter("timestamp");
    //随机数
    String nonce = request.getParameter("nonce");
    //随机字符串
    String echostr = request.getParameter("echostr");

    if (SignUtil.checkSignature(signature, timestamp, nonce)) {
      log.info(String.format("[signature: %s]<-->[timestamp: %s]<-->[nonce: %s]<-->[echostr: %s]",
          signature, timestamp, nonce, echostr));
      return echostr;
    } else {
      return "";
    }
  }
}
