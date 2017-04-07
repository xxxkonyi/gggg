package com.believe.core.repository;

import com.believe.core.domain.Manifesto;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 10:30
 * @since 1.0
 */
public interface ManifestoRepository extends MongoRepository<Manifesto, String> {
  Manifesto findByOpenId(String openId);

  Long countByOpenId(String openId);
}
