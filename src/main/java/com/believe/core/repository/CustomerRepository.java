package com.believe.core.repository;

import com.believe.core.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 23:47
 * @since 1.0
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {
  Customer findByOpenId(String openId);
}
