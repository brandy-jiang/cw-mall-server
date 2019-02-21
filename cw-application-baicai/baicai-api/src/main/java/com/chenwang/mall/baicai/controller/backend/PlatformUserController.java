package com.chenwang.mall.baicai.controller.backend;

import com.chenwang.mall.baicai.service.CustomerSignInRecordService;
import com.chenwang.mall.baicai.service.UserService;
import com.chenwang.mall.common.base.BaseController;
import com.chenwang.mall.common.base.BaseSpecification;
import com.chenwang.mall.dto.PageDto;
import com.chenwang.mall.dto.UserDto;
import com.chenwang.mall.dto.backend.CustomerDto;
import com.chenwang.mall.dto.backend.SignInDto;
import com.chenwang.mall.model.user.Customer;
import com.chenwang.mall.model.user.CustomerSignInRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/backend/user")
@Slf4j
public class PlatformUserController extends BaseController {
  @Autowired
  UserService userService;
  @Autowired
  CustomerSignInRecordService customerSignInRecordService;
  @RequestMapping(value="list",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public PageDto<CustomerDto> listB(CustomerDto dto, HttpServletRequest req){
    log.info(" request list {}", dto);
//    BannerDto user = (BannerDto) req.getSession().getAttribute("customer");
    Page<Customer> page = userService.findAll(userService.where("status","level","accountInfo.mobile","inviter.accountInfo.username").eq(1,dto.getLevel(),dto.getMobile(),dto.getInvCode())
                                              .and("accountInfo.nickname").like(dto.getNickname())
                                              .searchCustomized(), (dto.getPage() - 1) * PAGE_SIZE, PAGE_SIZE, DEFAULT_SORT);
    List<CustomerDto> result = CustomerDto.convert(page.getContent());
    PageDto pd = new PageDto();
    pd.setCount(page.getTotalElements());
    pd.setData(result);
    log.info("{}", pd);
    return pd;
  }

  @RequestMapping(value="/signIn/list",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public PageDto<SignInDto> listB(SignInDto dto, HttpServletRequest req){
    log.info(" request list {}", dto);
//    BannerDto user = (BannerDto) req.getSession().getAttribute("customer");
    Page<CustomerSignInRecord> page = customerSignInRecordService.findAll(customerSignInRecordService.where("status").eq(1)
        .and("customer.accountInfo.nickname").like(dto.getNickname())
        .searchCustomized(), (dto.getPage() - 1) * PAGE_SIZE, PAGE_SIZE, DEFAULT_SORT);
    List<SignInDto> result = SignInDto.convert(page.getContent());
    PageDto pd = new PageDto();
    pd.setCount(page.getTotalElements());
    pd.setData(result);
    log.info("{}", pd);
    return pd;
  }
  @RequestMapping(value="add",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String add(CustomerDto dto){
    log.info(" request add");
    try {
      userService.addCustomer(dto);
      return "{\"msg\":\"success\"}";
      //resp.sendRedirect(java.net.URLEncoder.encode("user_list.html", "utf-8"));
    } catch (Exception e) {
      e.printStackTrace();
      return "{\"msg\":\"添加用户失败\"}";
    }

  }
  @RequestMapping(value="view",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomerDto view(CustomerDto dto){
    log.info(" request view");
    try {
      return CustomerDto.convert(userService.findOne(dto.getId()));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  @RequestMapping(value="del",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String del(CustomerDto dto){
    log.info(" request del");
    try {
      userService.deleteById(dto.getId());
      return "{\"msg\":\"success\"}";
    } catch (Exception e) {
      e.printStackTrace();
      return "{\"msg\":\"error\"}";
    }
  }
}
