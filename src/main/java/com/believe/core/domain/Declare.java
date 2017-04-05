package com.believe.core.domain;

import com.believe.exception.ResponseException;
import com.believe.core.constant.SystemConstant;

import java.util.Set;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 23:13
 * @since 1.0
 */
public class Declare extends Domain {

  private String remark;

  /* 宣言人 */
  private String customerId;

  /* 点赞用户 */
  private Set<String> praiseUsers;

  /* 点赞次数 */
  private Integer praiseCount;

  public static Declare of(String remark, String customerId) {
    Declare declare = new Declare();
    declare.customerId = customerId;
    declare.remark = remark;
    return declare;
  }

  public boolean beAbleToPraise(String customerId) {
    return !(this.customerId.equals(customerId) || this.praiseUsers.contains(customerId));
  }

  public boolean beAbleToWinner() {
    return this.praiseCount >= SystemConstant.DELARE_QUOTA && this.praiseUsers.size() >= SystemConstant.DELARE_QUOTA;
  }

  public void praise(String praiseCustomerId) {
    if (!beAbleToPraise(praiseCustomerId)) {
      throw new ResponseException("你已经点过赞了");
    }
    this.praiseUsers.add(praiseCustomerId);
    this.praiseCount++;
  }

}
