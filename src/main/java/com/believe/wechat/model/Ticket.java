package com.believe.wechat.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 临时凭证
 */
@Data
public class Ticket implements Serializable {

  /**
   * 凭证字符串
   */
  private String ticket;

  /**
   * 凭证类型
   */
  private TicketType type;

  /**
   * 有效时间(s)
   */
  private Integer expire;

  /**
   * 过期时刻(ms)
   */
  private Long expireAt;
}
