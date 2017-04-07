package com.believe.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 13:59
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = "marketing", ignoreUnknownFields = false)
public class ApplicationProperties {

  private final WechatProperties wechat = new WechatProperties();

  private String resourceBase;
  private String winTemplateId;

  @Data
  public static class WechatProperties {
    private String appId;
    private String appSecret;
    private String authNotify;
  }

}
