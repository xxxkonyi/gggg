package com.believe.core.domain;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 22:46
 * @since 1.0
 */
public class Customer extends Domain {

  private String nickName;

  /* unique */
  private String openId;

  private String headImg;

  /* 点赞额度 */
  private Integer praiseQuota;

}
