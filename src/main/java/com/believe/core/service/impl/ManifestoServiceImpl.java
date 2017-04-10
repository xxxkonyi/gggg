package com.believe.core.service.impl;

import com.believe.core.domain.Customer;
import com.believe.core.domain.Manifesto;
import com.believe.core.repository.CustomerRepository;
import com.believe.core.repository.ManifestoRepository;
import com.believe.core.service.ManifestoService;
import com.believe.core.service.dto.PraiseResult;
import com.believe.exception.ResponseException;
import com.believe.wechat.model.TemplateParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

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
  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private WechatSupport wechatSupport;

  @Autowired
  private Executor executor;

  @Override
  public boolean beforeExist(String openId) {
    return manifestoRepository.countByOpenId(openId) > 0;
  }

  @Override
  public Manifesto getByIdentify(String identify) {
    Manifesto manifesto = manifestoRepository.findByOpenId(identify);
    return manifesto;
  }

  @Override
  public Manifesto get(String manifestoId) {
    return manifestoRepository.findOne(manifestoId);
  }

  @Override
  public Manifesto createManifesto(Customer creator, String remark) {
    Manifesto manifesto = Manifesto.of(remark, creator.getId(), creator.getOpenId());
    manifesto.setAvatar(creator.getAvatar());
    manifesto.setNickName(creator.getNickName());
    return manifestoRepository.save(manifesto);
  }

  @Override
  public PraiseResult praiseManifesto(String manifestoId, String praiseCustomerId) {
    Customer praiseCustomer = customerRepository.findOne(praiseCustomerId);
    PraiseResult result = null;
    if (null == praiseCustomer) {
      throw new ResponseException("customer.null");
    }
    if (!praiseCustomer.beAblePraise()) {
      throw new ResponseException("customer.praise.quota");
    }
    Manifesto manifesto = manifestoRepository.findOne(manifestoId);
    if (null == manifesto) {
      throw new ResponseException("manifesto.null");
    }
    manifesto.praise(praiseCustomerId);
    praiseCustomer.reducePraiseQuota();
    customerRepository.save(praiseCustomer);
    // 获奖
    if (manifesto.beAbleToWinner()) {
      result = PraiseResult.builder()
        .win(true)
        .self(praiseCustomerId.equals(manifesto.getCustomerId()))
        .build();
      this.executor.execute(() -> {
        TemplateParam templateParam = TemplateParam.builder()
          .openId(manifesto.getOpenId())
          .link("auth/address")
          .build();
        wechatSupport.sendWinMessages(templateParam);
      });
    }
    manifestoRepository.save(manifesto);
    return result;
  }

  @Override
  public Long countManifesto() {
    return manifestoRepository.count();
  }

  @Override
  public Page<Manifesto> findAll(Pageable pageable) {
    return manifestoRepository.findAll(pageable);
  }

}
