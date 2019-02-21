package com.chenwang.mall.dto.backend;

import com.chenwang.mall.common.utils.DateUtils;
import com.chenwang.mall.model.order.Order;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
  private String orderNo;
  private String createTime;
  private String payTime;
  private String shopType;
  private Integer type;
  private Long num;
  private Long delta;
  private Long score;
  private String logisticsName;
  private String logisticsNo;
  private String senderAddress;
  private String senderMobile;
  private String senderName;
  private String receiver;
  private String address;
  private String linkTel;
  private Integer payStatus;
  private String strPayStatus;
  private Long id;
  private Integer page;
  private Integer status;
  private String strStatus;
  private String startDate;
  private String endDate;
  private String customerName;

  private List<OrderDetailsDto> orderDetailsDtos;

  public static OrderDto convert(Order banner){
    OrderDto bannerDto = new OrderDto();
    bannerDto.setId(banner.getId());
    bannerDto.setOrderNo(banner.getOrderNo());
//    bannerDto.setProductName(banner.getProductName());
    bannerDto.setAddress(banner.getAddress());
    bannerDto.setDelta(banner.getDelta());
    bannerDto.setScore(banner.getScore());
    bannerDto.setCreateTime(DateUtils.format(banner.getCreateTime()));
    bannerDto.setCustomerName(banner.getCustomer().getAccountInfo().getNickname());
//    bannerDto.setShopType(banner.getProduct().getShopType() == 1?"上架":"下架");
//    bannerDto.setType(banner.getProduct().getShopType());
    bannerDto.setLinkTel(banner.getLinkTel());
    bannerDto.setLogisticsName(banner.getLogisticsName());
    bannerDto.setLogisticsNo(banner.getLogisticsNo());
    bannerDto.setOrderDetailsDtos(OrderDetailsDto.convert(banner.getOrderDetailsList()));
    bannerDto.setStatus(banner.getStatus());
    if(banner.getStatus() == 1){
      bannerDto.setStrStatus("已支付");
    }else if(banner.getStatus() == 3){
      bannerDto.setStrStatus("已发货");
    }else if(banner.getStatus() == 4){
      bannerDto.setStrStatus("已付款");
    }else{
      bannerDto.setStrStatus("未支付");
    }
//    bannerDto.setNum(banner.get);
//    bannerDto.setIntStatus(banner.getStatus());
//    if(banner.getStatus() == 1){
//      bannerDto.setStatus("上架");
//    }else{
//      bannerDto.setStatus("下架");
//    }
    return bannerDto;
  }

  public static List<OrderDto> convert(List<Order> bannerList){
    List<OrderDto> result = new ArrayList<>();
    bannerList.forEach(ea ->{
      OrderDto dto = convert(ea);
      result.add(dto);
    });
    return result;
  }

}
