package com.believe.admin;

import com.believe.core.domain.CustomerAddress;
import lombok.Data;

import java.io.Serializable;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/17 14:56
 * @since 1.0
 */
@Data
public class AddressDto implements Serializable {

  private String realName;
  private String mobilePhone;
  private String address;
  private String createdDate;

  public static AddressDto of(CustomerAddress customerAddress) {
    AddressDto dto = new AddressDto();
    dto.realName = customerAddress.getRealName();
    dto.mobilePhone = customerAddress.getMobilePhone();
    dto.address = customerAddress.getAddress();
    dto.createdDate = customerAddress.getCreatedDate().toString("yyyy-MM-dd HH:mm:ss");
    return dto;
  }

}
