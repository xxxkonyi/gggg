package com.believe.wechat;

import com.believe.exception.WechatException;
import com.believe.wechat.model.TemplateField;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

import static com.believe.utils.PreconditionUtils.checkNotBlank;

/**
 * 消息组件
 */
public final class Messages extends Component {

  /**
   * 发送模板消息
   */
  private static final String TEMPLATE_SEND = "http://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";


  /**
   * 向用户发送模版消息
   *
   * @param openId     用户openId
   * @param templateId 模版ID
   * @param fields     字段列表
   * @return 消息ID
   * @throws WechatException
   */
  public Long sendTemplate(String openId, String templateId, List<TemplateField> fields) {
    return sendTemplate(loadAccessToken(), openId, templateId, null, fields);
  }

  /**
   * 向用户发送模版消息
   *
   * @param accessToken accessToken
   * @param openId      用户openId
   * @param templateId  模版ID
   * @param fields      字段列表
   * @return 消息ID
   * @throws WechatException
   */
  public Long sendTemplate(String accessToken, String openId, String templateId, List<TemplateField> fields) {
    return sendTemplate(accessToken, openId, templateId, null, fields);
  }

  /**
   * 向用户发送模版消息
   *
   * @param openId     用户openId
   * @param templateId 模版ID
   * @param fields     字段列表
   * @param link       点击链接
   * @return 消息ID
   * @throws WechatException
   */
  public Long sendTemplate(String openId, String templateId, List<TemplateField> fields, String link) {
    return sendTemplate(loadAccessToken(), openId, templateId, link, fields);
  }

  /**
   * 向用户发送模版消息
   *
   * @param accessToken accessToken
   * @param openId      用户openId
   * @param templateId  模版ID
   * @param link        点击链接
   * @param fields      字段列表
   * @return 消息ID
   * @throws WechatException
   */
  public Long sendTemplate(String accessToken, String openId, String templateId, String link, List<TemplateField> fields) throws WechatException {
    checkNotBlank(accessToken);
    checkNotBlank(openId);
    checkNotBlank(templateId);

    String url = TEMPLATE_SEND + accessToken;
    Map<String, Object> params = buildTemplateParams(openId, templateId, link, fields);

    Map<String, Object> resp = doPost(url, params);
    Object msgId = resp.get("msgid");
    return msgId instanceof Long ? (Long) msgId : ((Integer) msgId).longValue();
  }

  private Map<String, Object> buildTemplateParams(String openId, String templateId, String link, List<TemplateField> fields) {
    Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
    params.put("touser", openId);
    params.put("template_id", templateId);
    if (!Strings.isNullOrEmpty(link)) {
      params.put("url", link);
    }
    if (fields != null && !fields.isEmpty()) {
      Map<String, Map<String, String>> data = Maps.newHashMapWithExpectedSize(fields.size());
      Map<String, String> dataItem;
      for (TemplateField field : fields) {
        dataItem = Maps.newHashMapWithExpectedSize(2);
        dataItem.put("value", field.getValue());
        dataItem.put("color", field.getColor());
        data.put(field.getName(), dataItem);
      }
      params.put("data", data);
    }
    return params;
  }

}
