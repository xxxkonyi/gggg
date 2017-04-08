package com.believe.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/8 15:34
 * @since 1.0
 */
@Document
@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends AbstractEntity {

  private String username;
  private String password;

  public boolean checkPassword(String password) {
    return this.password.equals(password);
  }

}
