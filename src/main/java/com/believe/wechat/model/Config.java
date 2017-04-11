package com.believe.wechat.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 调用JSSDK前需要加载的配置对象(http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html)
 */
@Data
public class Config implements Serializable {

  /**
   * 微信APP ID
   */
  private String appId;

  /**
   * 时间戳(秒)
   */
  private Long timestamp;

  /**
   * 随机字符串
   */
  private String nonceStr;

  /**
   * 签名
   */
  private String signature;

  public Config(String appId, Long timestamp, String nonceStr, String signature) {
    this.appId = appId;
    this.timestamp = timestamp;
    this.nonceStr = nonceStr;
    this.signature = signature;
  }

  Config() {

  }

}
