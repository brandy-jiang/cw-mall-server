package com.chenwang.mall.baicai.service;

import com.chenwang.mall.baicai.dao.UserBalanceRepository;
import com.chenwang.mall.common.base.BaseService;
import com.chenwang.mall.model.payment.UserBalance;
import com.chenwang.mall.model.user.Customer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class UserBalanceService extends BaseService<UserBalance, UserBalanceRepository> {
  @Transactional
  public UserBalance createBalance(Customer customer){
    if(customer.getUserBalance() == null){
      UserBalance userBalance = new UserBalance();
      userBalance.setDelta(0l);
      userBalance.setCustomer(customer);
      userBalance.setFrozen(0l);
      userBalance.setStatus(1);
      userBalance.setCreateTime(new Date());
      save(userBalance);
      customer.setUserBalance(userBalance);
      return userBalance;
    }
    return customer.getUserBalance();
  }
}
