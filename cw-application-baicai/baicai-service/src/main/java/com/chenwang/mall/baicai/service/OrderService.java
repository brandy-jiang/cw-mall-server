package com.chenwang.mall.baicai.service;

import com.chenwang.mall.baicai.dao.OrderRepository;
import com.chenwang.mall.common.base.BaseService;
import com.chenwang.mall.common.utils.DateUtils;
import com.chenwang.mall.dto.CartDto;
import com.chenwang.mall.dto.backend.ProductDto;
import com.chenwang.mall.model.order.Order;
import com.chenwang.mall.model.order.OrderDetails;
import com.chenwang.mall.model.payment.UserBalance;
import com.chenwang.mall.model.payment.UserFinanceWaterFlow;
import com.chenwang.mall.model.product.Product;
import com.chenwang.mall.model.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService extends BaseService<Order, OrderRepository> {

  @Autowired
  UserService userService;
  @Autowired
  ProductService productService;
  @Autowired
  UserFinanceWaterFlowService userFinanceWaterFlowService;

  @Transactional
  public Order createOrder(CartDto cartDto, Long id) {
    Customer customer = userService.findOne(id);
    Order order = new Order();
    order.setAddress(cartDto.getDefaultAddress().getAddress());
    order.setReceiver(cartDto.getDefaultAddress().getReceiver());
    order.setLinkTel(cartDto.getDefaultAddress().getLinkTel());
    order.setCustomer(customer);
    double all = cartDto.getProductDtoList().stream().filter(ea -> ea.getIsSelected() == 1).mapToDouble(ea -> ea.getNum() * ea.getPrice()).sum();
    order.setDelta((long)(all * 100));
    order.setLogisticsFee(0l);
    order.setOrderNo(DateUtils.format3(new Date()) + customer.getId());
    order.setStatus(0);
    order.setCreateTime(new Date());

    List<OrderDetails> detailsList = new ArrayList();
    cartDto.getProductDtoList().stream().filter(ea -> ea.getIsSelected() == 1).forEach(ea ->{
      OrderDetails orderDetails = new OrderDetails();
      orderDetails.setDelta((long)(ea.getPrice() * 100));
      Product product = productService.findOne(ea.getId());
      orderDetails.setProduct(product);
      orderDetails.setNum(ea.getNum());
      orderDetails.setProductName(product.getTitle());
      orderDetails.setScore(ea.getScore());
      orderDetails.setCreateTime(new Date());
      orderDetails.setStatus(1);
      detailsList.add(orderDetails);
    });
    order.setOrderDetailsList(detailsList);
    return save(order);
  }

  @Transactional
  public void payment(Order order, Customer customer) {
    Date now = new Date();
    order.setStatus(1);
    order.setUpdateTime(now);
    UserBalance userBalance = customer.getUserBalance();
    userBalance.setDelta(userBalance.getDelta() - order.getDelta());
    userBalance.setUpdateTime(now);

    UserFinanceWaterFlow userFinanceWaterFlow = new UserFinanceWaterFlow();
    userFinanceWaterFlow.setCustomer(customer);
    userFinanceWaterFlow.setOrder(order);
    userFinanceWaterFlow.setDelta(order.getDelta());
    userFinanceWaterFlow.setType(0);
    userFinanceWaterFlow.setAfterUpdatedDelta(userBalance.getDelta());
    userFinanceWaterFlow.setCreateTime(now);
    userFinanceWaterFlow.setStatus(1);
    userFinanceWaterFlowService.save(userFinanceWaterFlow);

    order.getOrderDetailsList().forEach(ea -> {
      Product product = ea.getProduct();
      product.setStock(product.getStock() - ea.getNum());
    });

  }
}
