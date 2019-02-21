package com.chenwang.mall.baicai.controller;

import com.chenwang.mall.baicai.service.OrderService;
import com.chenwang.mall.baicai.service.UserBalanceService;
import com.chenwang.mall.baicai.service.UserService;
import com.chenwang.mall.common.base.BaseController;
import com.chenwang.mall.dto.CartDto;
import com.chenwang.mall.dto.OrderDto;
import com.chenwang.mall.dto.PayResultDto;
import com.chenwang.mall.dto.UserDto;
import com.chenwang.mall.dto.backend.CustomerDto;
import com.chenwang.mall.dto.backend.ProductDto;
import com.chenwang.mall.model.order.Order;
import com.chenwang.mall.model.payment.UserBalance;
import com.chenwang.mall.model.user.Address;
import com.chenwang.mall.model.user.Customer;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class FTOrderController extends BaseController {
  @Autowired
  OrderService orderService;
  @Autowired
  UserService userService;
  @Autowired
  UserBalanceService userBalanceService;
  @RequestMapping(value="viewCart",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CartDto viewCart(HttpServletRequest req, HttpServletResponse resp){
    UserDto customerDto = (UserDto) getRequest().getSession().getAttribute("customer");
    if(customerDto == null) {
      log.info(" not login \n\n");
      CartDto c = new CartDto();
      c.setStatus(-1);
      return c;

    }
    log.info(" {} " , customerDto);
    CartDto cartDto = (CartDto) req.getSession().getAttribute("cart");
    if(cartDto == null){
      cartDto = new CartDto();

    }
    Customer customer = userService.findOne(customerDto.getId());
    List<Address> addresses = customer.getAccountInfo().getAddressList();
    if(addresses == null || addresses.size() == 0){
      return cartDto;
    }



    Address defaultAddress = addresses.stream().filter(ea -> ea.getIsDefault() == 1).findFirst().get();
    cartDto.setDefaultAddress(defaultAddress);


    req.getSession().setAttribute("cart", cartDto);
    log.info("cartDto : {}", cartDto);
    return cartDto;
  }

  @RequestMapping(value="create",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public OrderDto createOrder(HttpServletRequest req, HttpServletResponse resp){

    UserDto customerDto = (UserDto) getRequest().getSession().getAttribute("customer");
    if(customerDto == null) {
      log.info(" not login \n\n");
      CartDto c = new CartDto();
      c.setStatus(-1);
      return null;

    }
    //TODO check stock

    if(req.getSession().getAttribute("order") != null){
      return (OrderDto) req.getSession().getAttribute("order");
    }
    CartDto cartDto = (CartDto) req.getSession().getAttribute("cart");
    Order order = orderService.createOrder(cartDto, ((UserDto)getRequest().getSession().getAttribute("customer")).getId());
    OrderDto dto = new OrderDto();
    dto.setId(order.getId());
    dto.setAllPrice(order.getDelta() / 100. + "");
    dto.setOrderNo(order.getOrderNo());
    req.getSession().setAttribute("order", dto);
    return dto;
  }

  @RequestMapping(value="payment",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public PayResultDto payment(HttpServletRequest req, HttpServletResponse resp){
    PayResultDto dto = new PayResultDto();
    OrderDto orderDto = (OrderDto) req.getSession().getAttribute("order");
    if(orderDto == null){
      dto.setStatus(-1);
      dto.setMsg("订单不存在");
      return dto;
    }
    UserDto userDto = (UserDto)req.getSession().getAttribute("customer");
    if(userDto == null){
      dto.setStatus(-1);
      dto.setMsg("请先登录");
      return dto;
    }
    Customer customer = userService.findOne(userDto.getId());
    Long balance = userBalanceService.createBalance(customer).getDelta();

    Order order = orderService.findOne(orderDto.getId());
    if(balance < order.getDelta()){
      dto.setStatus(-1);
      dto.setMsg("余额不足 , 当前余额为 : " + balance / 100.);
      return dto;
    }
    orderService.payment(order, customer);

    getRequest().getSession().removeAttribute("order");
    getRequest().getSession().removeAttribute("cart");

    dto.setStatus(1);
    dto.setMsg("支付成功");
    return dto;
  }

}
