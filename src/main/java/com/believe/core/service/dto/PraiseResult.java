package com.believe.core.service.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/7 17:09
 * @since 1.0
 */
@Data
@Builder
public class PraiseResult implements Serializable {

  /*是否获奖*/
  private boolean isWin;
  private boolean isSelf;
}
