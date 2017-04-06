package com.believe.core.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 22:47
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode
public abstract class AbstractEntity implements Serializable {

  @Id
  private String id;

  private DateTime createdDate;
}
