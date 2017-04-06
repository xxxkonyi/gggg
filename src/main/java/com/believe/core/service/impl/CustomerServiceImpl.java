package com.believe.core.service.impl;

import com.believe.core.domain.Customer;
import com.believe.core.domain.CustomerAddress;
import com.believe.core.repository.CustomerAddressRepository;
import com.believe.core.repository.CustomerRepository;
import com.believe.core.service.CustomerService;
import com.believe.wechat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  public Customer get(String customerId) {
    return customerRepository.findOne(customerId);
  }

  @Override
  public Customer createCustomer(User user) {
    return null;
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
  public CustomerAddress createAddress(CustomerAddress address) {
    return null;
  }
}
