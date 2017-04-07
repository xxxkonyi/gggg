package com.believe.wechat.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/7 17:39
 * @since 1.0
 */
@Builder
@Data
public class TemplateParam {
  private String accessToken;
  private String openId;
  private String templateId;
  private String link;
  private List<TemplateField> fields;
}
