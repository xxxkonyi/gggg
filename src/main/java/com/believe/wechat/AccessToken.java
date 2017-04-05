package com.believe.wechat;

import lombok.Data;

import java.io.Serializable;

/**
 * 访问Token
 */
@Data
public class AccessToken implements Serializable {

  /**
   * accessToken
   */
  private String accessToken;

  /**
   * 有效时间(s)
   */
  private Integer expire;

  /**
   * 过期时刻(ms)
   */
  private Long expiredAt;

}
