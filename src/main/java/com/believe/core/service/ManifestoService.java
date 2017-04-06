package com.believe.core.service;

import com.believe.core.domain.Manifesto;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 10:34
 * @since 1.0
 */
public interface ManifestoService {

  Long count();

  Manifesto get(String manifestoId);

  Manifesto createManifesto(String customerId, String remark);

  void praiseManifesto(String manifestoId, String praiseCustomerId);

}
