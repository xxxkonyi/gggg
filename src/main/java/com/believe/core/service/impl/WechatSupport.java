package com.believe.core.service.impl;

import com.believe.core.config.ApplicationProperties;
import com.believe.wechat.Bases;
import com.believe.wechat.Users;
import com.believe.wechat.Wechat;
import com.believe.wechat.WechatBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 17:45
 * @since 1.0
 */
@Component
public class WechatSupport {

  private Wechat wechat;

  @Autowired
  private ApplicationProperties applicationProperties;

  @PostConstruct
  public void initWechat() {
    this.wechat = WechatBuilder.newBuilder(
      applicationProperties.getWechat().getAppId(),
      applicationProperties.getWechat().getAppSecret())
      .build();
  }

  public Bases bases() {
    return wechat.base();
  }

  public Users users() {
    return wechat.users();
  }

}
