package com.believe.core.service.impl;

import com.believe.core.domain.Manifesto;
import com.believe.core.repository.ManifestoRepository;
import com.believe.core.service.ManifestoService;
import com.believe.exception.ResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 10:34
 * @since 1.0
 */
@Service
public class ManifestoServiceImpl implements ManifestoService {

  @Autowired
  private ManifestoRepository manifestoRepository;

  @Override
  public Long count() {
    return manifestoRepository.count();
  }

  @Override
  public Manifesto get(String manifestoId) {
    return manifestoRepository.findOne(manifestoId);
  }

  @Override
  public Manifesto createManifesto(String customerId, String remark) {
    return manifestoRepository.save(Manifesto.of(remark, customerId));
  }

  @Override
  public void praiseManifesto(String manifestoId, String praiseCustomerId) {
    Manifesto manifesto = manifestoRepository.findOne(manifestoId);
    if (null == manifesto) {
      throw new ResponseException("manifesto.null");
    }
    manifesto.praise(praiseCustomerId);
  }
}
