package com.chenwang.mall.baicai.controller.backend;

import com.chenwang.mall.baicai.service.OrderService;
import com.chenwang.mall.common.base.BaseController;
import com.chenwang.mall.common.utils.DateUtils;
import com.chenwang.mall.dto.PageDto;
import com.chenwang.mall.dto.backend.OrderDto;
import com.chenwang.mall.model.order.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/backend/order")
@Slf4j
public class OrderController extends BaseController {
  @Autowired
  OrderService orderService;
  @RequestMapping(value="list",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public PageDto<OrderDto> listB(OrderDto dto, HttpServletRequest req){
    log.info(" request list {}", dto);
//    BannerDto user = (BannerDto) req.getSession().getAttribute("customer");
    Page<Order> page = orderService.findAll(orderService.where("status").eq(dto.getStatus())
        .and("orderNo", "customer.accountInfo.nickname").like(dto.getOrderNo(), dto.getCustomerName())
        .and("createTime").gt(DateUtils.parseDate(dto.getStartDate()))
        .and("createTime").lt(DateUtils.parseDate(dto.getEndDate()))
        .searchCustomized(), (dto.getPage() - 1) * PAGE_SIZE, PAGE_SIZE, DEFAULT_SORT);
    List<OrderDto> result = OrderDto.convert(page.getContent());
    PageDto pd = new PageDto();
    pd.setCount(page.getTotalElements());
    pd.setData(result);
    log.info("{}", pd);
    return pd;
  }

//  @RequestMapping(value="add",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//  public String add(OrderDto dto){
//    log.info(" request add");
//    try {
//      orderService.addOrder(dto);
//      return "{\"msg\":\"success\"}";
//      //resp.sendRedirect(java.net.URLEncoder.encode("user_list.html", "utf-8"));
//    } catch (Exception e) {
//      e.printStackTrace();
//      return "{\"msg\":\"添加用户失败\"}";
//    }
//
//  }
  @RequestMapping(value="view",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public OrderDto view(OrderDto dto){
    log.info(" request view");
    try {
      return OrderDto.convert(orderService.findOne(dto.getId()));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  @RequestMapping(value="del",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String del(OrderDto dto){
    log.info(" request del");
    try {
      orderService.deleteById(dto.getId());
      return "{\"msg\":\"success\"}";
    } catch (Exception e) {
      e.printStackTrace();
      return "{\"msg\":\"error\"}";
    }
  }
}
