package com.chenwang.mall.baicai.service;

import com.chenwang.mall.baicai.dao.UserRepository;

import com.chenwang.mall.common.base.BaseService;
import com.chenwang.mall.common.base.BaseSpecification;
import com.chenwang.mall.common.utils.MD5Utils;
import com.chenwang.mall.dto.UserDto;
import com.chenwang.mall.dto.WxUserInfoDto;
import com.chenwang.mall.dto.backend.CustomerDto;
import com.chenwang.mall.model.invite.InviteRelationship;
import com.chenwang.mall.model.payment.UserBalance;
import com.chenwang.mall.model.user.AccountInfo;
import com.chenwang.mall.model.user.Customer;
import com.chenwang.mall.model.user.CustomerSignInRecord;
import com.chenwang.mall.model.user.WechatUserInfo;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService extends BaseService<Customer, UserRepository> {
  @Autowired
  UserRepository userRepository;
  @Autowired
  CustomerSignInRecordService customerSignInRecordService;
  @Transactional
  public Customer autoRegister(WxUserInfoDto dto) {
    Customer customer = findByOpenId(dto.getOpenid());
    if(customer != null){
      return customer;
    }
    customer = new Customer();
    AccountInfo accountInfo = new AccountInfo();

    WechatUserInfo wechatUserInfo = new WechatUserInfo();
    Date now = new Date();

    wechatUserInfo.setWeixinId(dto.getOpenid());
    wechatUserInfo.setWxId(dto.getOpenid());
    wechatUserInfo.setWxUnionId(dto.getUnionid());
    wechatUserInfo.setCreateTime(now);
    wechatUserInfo.setStatus(1);


    accountInfo.setGender(dto.getSex());
    accountInfo.setHeadImgUrl(dto.getHeadimgurl());
    accountInfo.setNickname(dto.getNickname());
    accountInfo.setLastLoginTime(now);
    accountInfo.setCreateTime(now);
    accountInfo.setStatus(1);
    accountInfo.setUsername(dto.getNickname());
    accountInfo.setPassword(MD5Utils.MD5Encode("123456", "utf-8"));
    accountInfo.setWechatUserInfo(wechatUserInfo);

    customer.setCreateTime(now);
    customer.setStatus(1);
    customer.setAccountInfo(accountInfo);

    userRepository.save(customer);
    return customer;

  }
  public Customer findByOpenId(String openId){
    List<Customer> list = findAll(new BaseSpecification<Customer>().where("accountInfo.wechatUserInfo.weixinId").eq(openId).searchCustomized());
    if(list != null && list.size() > 0){
      return list.get(0);
    }
    return null;
  }
  @Transactional
  public void updateNickname(Long id, String nickname) {
    userRepository.findById(id).get().getAccountInfo().setNickname(nickname);
  }

  @Transactional
  public void updateUser(Long id, UserDto userDto) {
    Customer customer = userRepository.findById(id).get();
    if(StringUtils.isNotEmpty(userDto.getPayPassword()) && StringUtils.isNotEmpty(userDto.getRePayPassword()) && userDto.getRePayPassword().equals(userDto.getPayPassword())){
      customer.getAccountInfo().setPayPassword(userDto.getPayPassword());
    }
    if(StringUtils.isNotEmpty(userDto.getBankName())){
      customer.setBankName(userDto.getBankName());
    }
    if(StringUtils.isNotEmpty(userDto.getBankCardNo())){
      customer.setBankCardNo(userDto.getBankCardNo());
    }
  }
  @Transactional
  public Customer register(String invCode, String myCode, String password, String mobile, String nickname) throws Exception{
    //check mobile
    if(count(new BaseSpecification<Customer>().where("accountInfo.mobile").eq(mobile).searchCustomized()) > 0){
      throw new Exception("手机号已经注册,请直接登录");
    }

    AccountInfo accountInfo = new AccountInfo();
    accountInfo.setPassword(MD5Utils.MD5Encode(password, "utf-8"));
    accountInfo.setMobile(mobile);
    accountInfo.setUsername(myCode);
    accountInfo.setCreateTime(new Date());
    accountInfo.setStatus(1);
    accountInfo.setNickname(nickname);

    Customer customer = new Customer();
    customer.setAccountInfo(accountInfo);
    customer.setCreateTime(new Date());
    customer.setStatus(1);
    customer.setLevel(0);
    customer.setScore(0l);

    Customer customer1 = findAll(new BaseSpecification<Customer>().where("accountInfo.username").eq(invCode).searchCustomized()).get(0);

    InviteRelationship inviteRelationship = new InviteRelationship();
    inviteRelationship.setInviteTo(customer);
    inviteRelationship.setInviteFrom(customer1);
    inviteRelationship.setStatus(1);
    inviteRelationship.setCreateTime(new Date());
    List<InviteRelationship> ls = new ArrayList<>();
    ls.add(inviteRelationship);
    customer.setInviteRelationshipList(ls);

    UserBalance userBalance = new UserBalance();
    userBalance.setCreateTime(new Date());
    userBalance.setStatus(1);
    userBalance.setFrozen(0l);
    userBalance.setDelta(0l);
    userBalance.setBcl(BigDecimal.ZERO);

    customer.setUserBalance(userBalance);

    return save(customer);

  }

  public Customer login(String password, String mobile) {
    return findAll(new BaseSpecification<Customer>().where("accountInfo.mobile").eq(mobile).and("accountInfo.password").eq(MD5Utils.MD5Encode(password, "utf-8")).searchCustomized()).get(0);
  }

  public Boolean checkMobileExist(String mobile) {
    if(count(new BaseSpecification<Customer>().where("accountInfo.mobile").eq(mobile).searchCustomized()) > 0){
      return true;
    }
    return false;
  }
  @Transactional
  public void addCustomer(CustomerDto dto) throws Exception{
    if(dto.getId() != null){
      updateCustomer(dto);
      return;
    }
    if(count(where("accountInfo.mobile").eq(dto.getMobile()).searchCustomized()) > 0){
      throw new Exception("手机号已经注册,请直接登录");
    }

    AccountInfo accountInfo = new AccountInfo();
    accountInfo.setPassword(MD5Utils.MD5Encode(dto.getPassword(), "utf-8"));
    accountInfo.setMobile(dto.getMobile());
    accountInfo.setUsername(dto.getUsername());
    accountInfo.setCreateTime(new Date());
    accountInfo.setStatus(1);
    accountInfo.setNickname(dto.getUsername());


    Customer customer = new Customer();
    customer.setAccountInfo(accountInfo);
    customer.setCreateTime(new Date());
    customer.setStatus(1);
    customer.setBankCardNo(dto.getBankCardNo());
    customer.setBankName(dto.getBankName());
    customer.setScore(dto.getScore());
    customer.setLevel(dto.getLevel());

    Customer customer1 = findAll(new BaseSpecification<Customer>().where("accountInfo.username").eq(dto.getInvCode()).searchCustomized()).get(0);
    if(customer1 == null){
      throw new Exception("推荐人不存在");
    }

    customer.setInviter(customer1);
    InviteRelationship inviteRelationship = new InviteRelationship();
    inviteRelationship.setInviteTo(customer);
    inviteRelationship.setInviteFrom(customer1);
    inviteRelationship.setStatus(1);
    inviteRelationship.setCreateTime(new Date());
    List<InviteRelationship> ls = new ArrayList<>();
    ls.add(inviteRelationship);
    customer.setInviteRelationshipList(ls);

    save(customer);
  }

  private void updateCustomer(CustomerDto dto) {
    Customer customer = findOne(dto.getId());
    customer.setScore(dto.getScore());
    customer.setLevel(dto.getLevel());
    customer.setBankName(dto.getBankName());
    customer.setBankCardNo(dto.getBankCardNo());
    customer.setUpdateTime(new Date());
    customer.getAccountInfo().setNickname(dto.getUsername());
    customer.getAccountInfo().setPassword(dto.getPassword());
    customer.getAccountInfo().setMobile(dto.getMobile());
  }
  @Transactional
  public void modifyName(Long id, String nickname) {
    Customer customer = findOne(id);
    customer.getAccountInfo().setNickname(nickname);
  }
  @Transactional
  public Customer modifyPwd(Long id, String password, String newPassword) {
    Customer customer = findOne(id);
    if(customer.getAccountInfo().getPassword().equals(MD5Utils.MD5Encode(password, "utf-8"))){
      customer.getAccountInfo().setPassword(MD5Utils.MD5Encode(newPassword, "utf-8"));
    }else{
      return null;
    }
    return customer;
  }

  @Transactional
  public boolean userCheckIn(Long id) {
    CustomerSignInRecord customerSignInRecord = new CustomerSignInRecord();
    Customer customer = findOne(id);

    if(customer.getCustomerSignInRecordList() != null && customer.getCustomerSignInRecordList().size() > 0){
      CustomerSignInRecord customerSignInRecord1 = customer.getCustomerSignInRecordList().get(0);
      if(customerSignInRecord1.getCreateTime().after(new DateTime(new Date()).toLocalDate().toDate())){
        return false;
      }
    }
    customer.getUserBalance().setBcl(customer.getUserBalance().getBcl() == null ? BigDecimal.ZERO : customer.getUserBalance().getBcl());
    if(customer.getLevel() == 0){
      customerSignInRecord.setSignInPoint(BigDecimal.valueOf(0.5));
      customer.getUserBalance().setBcl(customer.getUserBalance().getBcl().add(BigDecimal.valueOf(0.5)));
    }else {
      customerSignInRecord.setSignInPoint(BigDecimal.valueOf(1.0));
      customer.getUserBalance().setBcl(customer.getUserBalance().getBcl().add(BigDecimal.valueOf(1.0)));
    }
    customerSignInRecord.setCustomer(customer);
    customerSignInRecord.setCreateTime(new Date());
    customerSignInRecord.setStatus(1);
    customerSignInRecordService.save(customerSignInRecord);


    return true;
  }
}
