package com.believe.core.service.impl;

import com.believe.core.config.ApplicationProperties;
import com.believe.utils.PreconditionUtils;
import com.believe.wechat.*;
import com.believe.wechat.model.Config;
import com.believe.wechat.model.TemplateField;
import com.believe.wechat.model.TemplateParam;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.*;

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

  public JsSdks jsSdks() {
    return wechat.js();
  }

  public Messages messages() {
    return wechat.messages();
  }

  public Long sendWinMessages(TemplateParam templateParam) {
    Messages messages = wechat.messages();
    List<TemplateField> templateFields = Lists.newArrayList(
      new TemplateField("first", "请点击该消息填写地址", "#6bfafa"),
      new TemplateField("keyword1", "高原精装奶粉", "#6bfafa"),
      new TemplateField("keyword2", "", "#6bfafa"),
      new TemplateField("keyword3", "", "#6bfafa"),
      new TemplateField("keyword4", "", "#6bfafa"),
      new TemplateField("remark", "如果超过时间未填写，视为放弃奖品！", "#6bfafa")
    );
    templateParam.setFields(templateFields);
    templateParam.setLink(applicationProperties.getResourceBase() + templateParam.getLink());
    return messages.sendTemplate(templateParam.getOpenId(),
      applicationProperties.getWinTemplateId(),
      templateParam.getFields(),
      templateParam.getLink());
  }

  public String authUrl(String path) {
    return bases().authUrl(applicationProperties.getWechat().getAuthNotify(), path);
  }

  public Config jsConfig(String url) {
    PreconditionUtils.checkNotBlank(url);
    url = applicationProperties.getWechat().getJsNotify() + url;
    return jsSdks().getConfig(randomAlphanumeric(16), url);
  }
}
