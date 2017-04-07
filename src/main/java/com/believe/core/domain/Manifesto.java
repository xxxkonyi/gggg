package com.believe.core.domain;

import com.believe.exception.ResponseException;
import com.believe.core.constant.SystemConstant;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 23:13
 * @since 1.0
 */
@Document
@Data
@EqualsAndHashCode(callSuper = true)
public class Manifesto extends AbstractEntity {

  private String remark;

  /* 宣言人 */
  private String customerId;
  private String openId;
  private String nickName;
  private String avatar;

  /* 点赞用户 */
  private Set<String> praiseUsers;

  /* 点赞次数 */
  private Integer praiseCount;

  public static Manifesto of(String remark, String customerId, String openId) {
    Manifesto manifesto = new Manifesto();
    manifesto.customerId = customerId;
    manifesto.remark = remark;
    manifesto.openId = openId;
    manifesto.praiseCount = 0;
    manifesto.praiseUsers = Sets.newHashSet();
    return manifesto;
  }

  public boolean isWined() {
    return this.praiseCount >= SystemConstant.MANIFESTO_QUOTA;
  }

  public boolean beAbleToPraise(String customerId) {
    return !(this.customerId.equals(customerId) || this.praiseUsers.contains(customerId));
  }

  public boolean beAbleToWinner() {
    return this.praiseCount >= SystemConstant.MANIFESTO_QUOTA && this.praiseUsers.size() >= SystemConstant.MANIFESTO_QUOTA;
  }

  public void praise(String praiseCustomerId) {
    if (praiseCustomerId.equals(this.customerId)) {
      throw new ResponseException("manifesto.praise.self");
    }
    if (!beAbleToPraise(praiseCustomerId)) {
      throw new ResponseException("manifesto.praise.repeat");
    }
    this.praiseUsers.add(praiseCustomerId);
    this.praiseCount++;
  }

}
