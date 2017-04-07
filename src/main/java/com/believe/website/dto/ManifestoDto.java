package com.believe.website.dto;

import com.believe.core.domain.Customer;
import com.believe.core.domain.Manifesto;
import lombok.Data;

import java.io.Serializable;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 22:12
 * @since 1.0
 */
@Data
public class ManifestoDto implements Serializable {

  private String remark;

  private String manifestoId;
  private int praiseCount;
  private String customerId;
  private String openId;
  private String nickName;
  private String avatar;

  public static ManifestoDto of(Manifesto manifesto) {
    ManifestoDto dto = new ManifestoDto();
    dto.remark = manifesto.getRemark();
    dto.customerId = manifesto.getCustomerId();
    dto.openId = manifesto.getOpenId();
    dto.nickName = manifesto.getNickName();
    dto.avatar = manifesto.getAvatar();
    dto.praiseCount = manifesto.getPraiseCount();
    dto.manifestoId = manifesto.getId();
    return dto;
  }

  public static ManifestoDto of(Customer customer) {
    ManifestoDto dto = new ManifestoDto();
    dto.customerId = customer.getId();
    dto.openId = customer.getOpenId();
    dto.nickName = customer.getNickName();
    dto.avatar = customer.getAvatar();
    return dto;
  }

}
