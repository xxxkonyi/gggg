package com.believe.core.service.impl;

import com.believe.core.constant.SystemConstant;
import com.believe.core.domain.Customer;
import com.believe.core.domain.CustomerAddress;
import com.believe.core.repository.CustomerAddressRepository;
import com.believe.core.repository.CustomerRepository;
import com.believe.core.service.CustomerService;
import com.believe.wechat.model.User;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 23:51
 * @since 1.0
 */
@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private CustomerAddressRepository customerAddressRepository;

  @Override
  public boolean beforeExistAddress(String openId) {
    return customerAddressRepository.countByOpenId(openId) > 0;
  }

  @Override
  public Optional<Customer> identify(String customerId) {
    Customer customer = customerRepository.findOne(customerId);
    if (null == customer) {
      customer = customerRepository.findByOpenId(customerId);
    }
    return Optional.of(customer);
  }

  @Override
  public Customer updateCustomer(String openId, User user) {
    Customer customer = customerRepository.findByOpenId(openId);
    DateTime now = DateTime.now();
    if (null == customer) {
      customer = new Customer();
      customer.setLastUpdateDate(now);
      customer.setPraiseQuota(SystemConstant.CUSTOMER_PRAISE_QUOTA);
    }
    customer.setOpenId(user.getOpenId());
    customer.setNickName(user.getNickName());
    customer.setAvatar(user.getHeadImgUrl());
    customer.setSex(user.getSex());
    customer.setCreatedDate(now);
    customer.updateCount();
    return customerRepository.save(customer);
  }

  //todo test
  @Override
  public Customer createCustomer(String nickName) {
    Customer customer = new Customer();
    customer.setOpenId(nickName);
    customer.setNickName(nickName);
    customer.setAvatar(nickName);
    return customerRepository.save(customer);
  }

  @Override
  public CustomerAddress createAddress(String customerId, CustomerAddress address) {
    CustomerAddress newAddress = new CustomerAddress();
    newAddress.setAddress(address.getAddress());
    newAddress.setCustomerId(customerId);
    newAddress.setMobilePhone(address.getMobilePhone());
    newAddress.setRealName(address.getRealName());
    return customerAddressRepository.save(newAddress);
  }
}
