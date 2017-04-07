package com.believe.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 22:46
 * @since 1.0
 */
@Document
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends AbstractEntity {

  /* unique */
  private String openId;
  private String nickName;
  private Integer sex;
  private String avatar;

  /* 点赞额度 */
  private Integer praiseQuota;

  private int loginCount;
  private DateTime lastUpdateDate;

  public void updateCount() {
    this.loginCount++;
  }

  public boolean beAblePraise() {
    return this.praiseQuota > 0;
  }

  public void reducePraiseQuota() {
    if (beAblePraise()) {
      this.praiseQuota--;
    }
  }

}
