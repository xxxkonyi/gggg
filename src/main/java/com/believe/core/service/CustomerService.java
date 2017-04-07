package com.believe.core.service;

import com.believe.core.domain.Customer;
import com.believe.core.domain.CustomerAddress;
import com.believe.wechat.model.User;

import java.util.Optional;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 23:50
 * @since 1.0
 */
public interface CustomerService {

  boolean beforeExistAddress(String openId);

  Optional<Customer> identify(String customerId);

  Customer updateCustomer(String openId, User user);

  Customer createCustomer(String nickName);

  CustomerAddress createAddress(String customerId, CustomerAddress address);

}
