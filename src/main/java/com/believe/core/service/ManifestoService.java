package com.believe.core.service;

import com.believe.core.domain.Manifesto;
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

  Manifesto get(String manifestoId);

  Manifesto createManifesto(String customerId, String openId, String remark);

  void praiseManifesto(String manifestoId, String praiseCustomerId);

  Long countManifesto();

  Page<Manifesto> findAll(Pageable pageable);
}
