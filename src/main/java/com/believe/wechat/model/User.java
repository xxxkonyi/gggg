package com.believe.wechat.model;

import com.believe.core.serializer.DateDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 */
@Data
public class User implements Serializable {

  /**
   * 0未关注，1已关注
   */
  private Integer subscribe;

  @JsonProperty("openid")
  private String openId;

  @JsonProperty("nickname")
  private String nickName;

  /**
   * 0未知，1男，2女
   */
  private Integer sex;

  private String city;

  private String province;

  private String country;

  @JsonProperty("headimgurl")
  private String headImgUrl;

  @JsonProperty("subscribe_time")
  @JsonDeserialize(using = DateDeserializer.class)
  private Date subscribeTime;

  @JsonProperty("unionid")
  private String unionId;

  private String remark;

  @JsonProperty("groupid")
  private Integer groupId;

}
