package com.believe.core.repository;

import com.believe.core.domain.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/8 15:34
 * @since 1.0
 */
public interface AdminRepository extends MongoRepository<Admin, String> {

  Admin findByUsername(String username);
}
