package com.believe.core.service;

import com.believe.core.domain.Customer;
import com.believe.core.domain.Manifesto;
import com.believe.core.service.dto.PraiseResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 10:34
 * @since 1.0
 */
public interface ManifestoService {

  boolean beforeExist(String openId);

  Manifesto getByIdentify(String identify);

  Manifesto get(String manifestoId);

  Manifesto createManifesto(Customer creator, String remark);

  PraiseResult praiseManifesto(String manifestoId, String praiseCustomerId);

  Long countManifesto();

  Page<Manifesto> findAll(Pageable pageable);
}
