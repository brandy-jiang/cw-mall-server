package com.chenwang.mall.baicai.controller.backend;

import com.chenwang.mall.baicai.service.backend.PlatformUserService;
import com.chenwang.mall.common.base.BaseController;
import com.chenwang.mall.common.utils.MD5Utils;
import com.chenwang.mall.dto.UserDto;
import com.chenwang.mall.dto.backend.PlatformLoginDto;
import com.chenwang.mall.model.platform.PlatformUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/platform")
@Slf4j
public class PlatformLoginController extends BaseController {
  @Autowired
  PlatformUserService platformUserService;
  @RequestMapping(value="login",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String login(PlatformLoginDto platformLoginDto){
    Specification<PlatformUser> c = platformUserService.where("username").eq(platformLoginDto.getUsername()).and("password").eq(MD5Utils.MD5Encode(platformLoginDto.getPassword(),"utf-8")).searchCustomized();
    List<PlatformUser> ls = platformUserService.findAll(c);
    if(ls != null && ls.size() > 0){
      PlatformLoginDto dto = new PlatformLoginDto();
      PlatformUser user = (PlatformUser)ls.get(0);
      dto.setLoginTime(new Date());
      dto.setPassword(user.getPassword());
      dto.setId(user.getId());
      dto.setUsername(user.getUsername());

      getRequest().getSession().setAttribute("platformUser", dto);
      return "{\"msg\":\"success\"}";
    }else{
      log.error("用户名密码错误");
      return "{\"msg\":\"用户名密码错误\"}";
    }
  }

  @RequestMapping(value="logout",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String logout(PlatformLoginDto platformLoginDto){
      getRequest().getSession().removeAttribute("platformUser");
      return "success";

  }
}

