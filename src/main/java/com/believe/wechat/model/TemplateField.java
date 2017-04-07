package com.believe.wechat.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 模版消息字段
 */
@Data
public class TemplateField implements Serializable {
  /**
   * 名称
   */
  private String name;

  /**
   * 值
   */
  private String value;

  /**
   * 颜色
   */
  private String color;

  public TemplateField() {
  }


  public TemplateField(String name, String value, String color) {
    this.name = name;
    this.value = value;
    this.color = color;
  }

  public TemplateField(String name, String value) {
    this.name = name;
    this.value = value;
  }


}
