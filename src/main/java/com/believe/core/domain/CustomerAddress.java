package com.believe.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 23:19
 * @since 1.0
 */
@Document
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerAddress extends AbstractEntity {

  private String manifestoId;
  private String customerId;
  private String openId;
  private boolean mark;

  private String realName;
  private String mobilePhone;
  private String address;

}
