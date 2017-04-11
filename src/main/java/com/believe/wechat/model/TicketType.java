package com.believe.wechat.model;

/**
 * 临时凭证类型
 */
public enum TicketType {

  JSAPI("jsapi");

  private String type;

  TicketType(String type) {
    this.type = type;
  }

  public String type() {
    return type;
  }
}