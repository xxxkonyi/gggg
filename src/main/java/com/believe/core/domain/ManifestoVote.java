package com.believe.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 10:10
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ManifestoVote extends AbstractEntity {

  private String openId;
  private String name;
  private String avatar;

  private String userInfo;

}
