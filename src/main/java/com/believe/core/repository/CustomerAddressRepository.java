package com.believe.core.repository;

import com.believe.core.domain.CustomerAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 10:40
 * @since 1.0
 */
public interface CustomerAddressRepository extends MongoRepository<CustomerAddress, String> {
  Long countByOpenId(String openId);

  Page<CustomerAddress> findByMobilePhone(String mobilePhone, Pageable pageable);
}
