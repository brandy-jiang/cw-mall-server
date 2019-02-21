package com.chenwang.mall.baicai.controller;

import com.chenwang.mall.baicai.service.UserService;

import com.chenwang.mall.common.base.BaseController;
import com.chenwang.mall.common.utils.QRCodeUtils;
import com.chenwang.mall.dto.UserBalanceDto;
import com.chenwang.mall.dto.UserDto;
import com.chenwang.mall.model.user.Address;
import com.chenwang.mall.model.user.Customer;
import com.chenwang.mall.sms.SMSService;
import com.google.zxing.WriterException;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController extends BaseController {
  static SimpleDateFormat sdf  = new SimpleDateFormat("yyMMdd");
  static String today = sdf.format(new Date());
  static Integer seq = 1;

  @Autowired
  UserService userService;
  @Autowired
  SMSService smsService;
  @Autowired
  QRCodeUtils qrCodeUtils;

  @RequestMapping(value="genNewCode",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public UserDto genNewCode(){
    String code = newCode();
    UserDto dto = new UserDto();
    dto.setUsername(code);
    return dto;
  }

  @RequestMapping(value="register",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String register(@RequestParam(value="invCode") String invCode,
                      @RequestParam(value="myCode") String myCode,
                      @RequestParam(value="nickname") String nickname,
                      @RequestParam(value="password") String password,
                      @RequestParam(value="mobile") String mobile,
                      @RequestParam(value="smsCode") String smsCode,
                      HttpServletRequest req, HttpServletResponse response){
    log.info(" request user register");
    String code = (String) req.getSession().getAttribute("smsCode");
    if(!code.equals(smsCode)){
      return "短信验证码错误";
    }
    log.info("{}", code);
    Customer customer = null;
    try {
      customer = userService.register(invCode, myCode, password, mobile , nickname);
      response.sendRedirect("/success.html");
    } catch (Exception e) {

      log.error("{}", e);
      if(e.getMessage().indexOf("手机号已经注册") >= 0){
        return e.getMessage();
      }



    }
    return null;

  }

  @RequestMapping(value="login",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public void login(@RequestParam(value="mobile") String mobile,
                   @RequestParam(value="password") String password,
                   HttpServletRequest req, HttpServletResponse response){
    log.info(" request user register");
    String code = (String) req.getSession().getAttribute("smsCode");
//    if(!code.equals(smsCode)){
//      return ;
//    }
    log.info("{}", code);
    Customer customer = null;
    try {
      customer = userService.login( password, mobile);
      req.getSession().setAttribute("customer", UserDto.convert(customer));
      response.sendRedirect("/xuzhi.html");
    } catch (Exception e) {

      log.error("{}", e);

    }

  }

  @RequestMapping(value="smsCode",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public UserDto randCode(@RequestParam(value="mobile") String mobile, HttpServletRequest req){
    log.info(" request randCode");
    UserDto dto = new UserDto();
    if(!isMobile(mobile)){
      dto.setUsername("手机号格式非法");
      return dto;
    }
    boolean result = userService.checkMobileExist(mobile);
    if(result){
      dto.setUsername("手机号已经注册");
      return dto;
    }
    String random = new Random().nextInt(9) + "" + new Random().nextInt(9)
            + "" + new Random().nextInt(9) + "" + new Random().nextInt(9) +  "" + new Random().nextInt(9) + "" + new Random().nextInt(9);
    smsService.sendCode(mobile, random, "白菜网" );
    req.getSession().setAttribute("smsCode", random);
    log.info("{}", random);
    dto.setUsername("发送成功");
    return dto;
  }

  @RequestMapping(value="profile",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public UserDto test(HttpServletRequest req, HttpServletResponse resp){
    log.info(" request user profile");
    UserDto user = (UserDto) req.getSession().getAttribute("customer");
    if(user == null){
      try {
        resp.sendRedirect("login.html");
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }
    user = UserDto.convert(userService.findOne(user.getId()));
    log.info("{}", user);
    return user;
  }

  @RequestMapping(value="updateName",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public void test(@RequestParam(value="nickname") String nickname, HttpServletRequest req, HttpServletResponse resp){
    log.info(" request user profile");
    UserDto user = (UserDto)req.getSession().getAttribute("customer");
    userService.updateNickname(user.getId(), nickname);
    user.setNickname(nickname);
    req.getSession().setAttribute("customer", user);
    try {
      resp.sendRedirect("/datum.html");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value="update",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public void test(UserDto userDto, HttpServletRequest req, HttpServletResponse resp){
    log.info(" request user profile");
    UserDto user = (UserDto)req.getSession().getAttribute("customer");
    userService.updateUser(user.getId(), userDto);
//    user.setNickname(nickname);
//    req.getSession().setAttribute("customer", user);
    try {
      resp.sendRedirect("/datum.html");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value="addressList",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public List<Address> addressList(HttpServletRequest req, HttpServletResponse resp){
    log.info(" request user addressList");
    UserDto user = (UserDto)req.getSession().getAttribute("customer");
    return userService.findOne(user.getId()).getAccountInfo().getAddressList();
  }

  @RequestMapping(value="getShareImg",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public UserDto getShareImg(HttpServletRequest req, HttpServletResponse resp){
    log.info(" request user profile");
    UserDto user = (UserDto)req.getSession().getAttribute("customer");
    if(user == null)
      return new UserDto();
    Long id = user.getId();
    Customer customer = userService.findOne(user.getId());
    try {
      qrCodeUtils.generateQRCodeImage("http://115.159.41.148:28080/register.html?invCode=" + customer.getAccountInfo().getUsername(), 200, 200, "/share-" + id + ".png");
    } catch (WriterException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    UserDto userDto = new UserDto();
    userDto.setUsername(customer.getAccountInfo().getUsername());
    userDto.setId(customer.getId());
    return userDto;
  }

  @RequestMapping(value="modifyName",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public UserDto modifyName(UserDto dto , HttpServletRequest req, HttpServletResponse resp){
    log.info(" request user profile");
    UserDto user = (UserDto)req.getSession().getAttribute("customer");
    if(user == null)
      return new UserDto();
    Long id = user.getId();
    userService.modifyName(user.getId(), dto.getNickname());

    return dto;
  }

  @RequestMapping(value="modifyPwd",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public UserDto modifyPwd(UserDto dto , HttpServletRequest req, HttpServletResponse resp){
    log.info(" request user profile");
    UserDto user = (UserDto)req.getSession().getAttribute("customer");
    if(user == null)
      return new UserDto();
    Long id = user.getId();
    Customer customer = userService.modifyPwd(user.getId(), dto.getPassword(), dto.getNewPassword());
    if(customer == null){
      dto.setUsername("原密码错误");
    }else{
      dto.setUsername("修改成功");
    }
    return dto;
  }
  @RequestMapping(value="checkIn",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public UserDto checkIn(UserDto dto , HttpServletRequest req, HttpServletResponse resp){
    log.info(" request user profile");
    UserDto user = (UserDto)req.getSession().getAttribute("customer");
    if(user == null)
      return new UserDto();
    Long id = user.getId();
    boolean result = userService.userCheckIn(user.getId());
    if(result){
      dto.setUsername("success");
    }

    return dto;
  }

  @RequestMapping(value="wallet",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public UserBalanceDto wallet(UserDto dto , HttpServletRequest req){
    log.info(" request user profile");
    UserDto user = (UserDto)req.getSession().getAttribute("customer");
    if(user == null)
      return new UserBalanceDto();
    Long id = user.getId();
    UserBalanceDto userBalanceDto = new UserBalanceDto();
    Customer customer = userService.findOne(id);
    userBalanceDto.setBalance(customer.getUserBalance().getDelta() / 100. + "");
    userBalanceDto.setBcl(customer.getUserBalance().getBcl() + "");
    userBalanceDto.setScore(customer.getScore() + "");
    userBalanceDto.setDeviceNum(customer.getDeviceList() == null ? 0l : customer.getDeviceList().size());
    userBalanceDto.setId(customer.getUserBalance().getId());

    return userBalanceDto;
  }

  private synchronized String newCode(){
    seq ++;
    String newDate = sdf.format(new Date());
    if(!today.equals(newDate)){
      today = newDate;
      seq = 1;
    }
    return sdf.format(new Date()) + makeUpSeq(seq);
  }
  private String makeUpSeq(Integer i){
    if(i < 10){
      return "000" + i;
    }else if(i < 100){
      return "00" + i;
    }else if(i < 1000){
      return "0" + i;
    }else{
      return String.valueOf(seq);
    }
  }

  private boolean isMobile(final String str) {
    Pattern p = null;
    Matcher m = null;
    boolean b = false;
    p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
    m = p.matcher(str);
    b = m.matches();
    return b;
  }

}
