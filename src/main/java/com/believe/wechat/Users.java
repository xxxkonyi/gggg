package com.believe.wechat;

import com.believe.utils.PreconditionUtils;
import com.believe.wechat.model.User;

import java.util.Map;

import static com.believe.utils.PreconditionUtils.*;

/**
 * 用户组件
 */
public final class Users extends Component {

  /**
   * 由AccessToken拉取用户信息
   */
  private static final String GET_SNS_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?";

  /**
   * 拉取用户信息
   */
  private static final String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?lang=zh_CN&access_token=";

  /**
   * 拉取用户信息(若用户未关注，且未授权，将拉取不了信息)
   *
   * @param openId 用户openId
   * @return 用户信息
   * @throws com.believe.exception.WechatException
   */
  public User getUser(String openId) {
    return getUser(loadAccessToken(), openId);
  }

  /**
   * 拉取用户信息(若用户未关注，且未授权，将拉取不了信息)
   *
   * @param accessToken accessToken
   * @param openId      用户openId
   * @return 用户信息，
   * @throws com.believe.exception.WechatException
   */
  public User getUser(String accessToken, String openId) {
    checkNotBlank(accessToken);
    checkNotBlank(openId);
    String url = GET_USER_INFO + accessToken + "&openid=" + openId;
    Map<String, Object> resp = doGet(url);

    return JsonUtils.DEFAULT.fromJson(JsonUtils.DEFAULT.toJson(resp), User.class);
  }

}
