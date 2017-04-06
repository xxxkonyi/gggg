package com.believe.core.service;

import com.believe.core.domain.Customer;
import com.believe.core.domain.CustomerAddress;
import com.believe.wechat.model.User;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 23:50
 * @since 1.0
 */
public interface CustomerService {

  Customer get(String customerId);

  Customer createCustomer(User user);

  Customer createCustomer(String nickName);

  CustomerAddress createAddress(String customerId, CustomerAddress address);

}
