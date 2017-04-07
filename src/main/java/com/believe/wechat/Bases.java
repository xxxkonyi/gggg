package com.believe.wechat;

import com.believe.exception.WechatException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import static com.believe.utils.PreconditionUtils.*;

/**
 * 基础组件
 */
public final class Bases extends Component {

  /**
   * 授权
   */
  private static final String AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?";

  /**
   * 获取用户openId
   */
  private static final String OPEN_ID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?";

  /**
   * 刷新用户授权Token
   */
  private static final String AUTH_ACCESS_TOKEN_REFRESH = "https://api.weixin.qq.com/sns/oauth2/refresh_token?";

  /**
   * 获取accessToken
   */
  private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";


  /**
   * 构建授权跳转URL(静默授权，仅获取用户openId，不包括个人信息)
   *
   * @param redirectUrl 授权后的跳转URL(我方服务器URL)
   * @return 微信授权跳转URL
   */
  public String authUrl(String redirectUrl) {
    return authUrl(redirectUrl, "index", Boolean.TRUE);
  }

  public String authUrl(String redirectUrl, String state) {
    return authUrl(redirectUrl, state, Boolean.FALSE);
  }

  /**
   * 构建授权跳转URL
   *
   * @param redirectUrl 授权后的跳转URL(我方服务器URL)
   * @param quiet       是否静默: true: 仅获取openId，false: 获取openId和个人信息(需用户手动确认)
   * @return 微信授权跳转URL
   */
  public String authUrl(String redirectUrl, String state, Boolean quiet) {
    try {
      checkNotBlank(redirectUrl);
      redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
      return new StringBuilder(AUTH_URL).append("appid=")
        .append(wechat.getAppId())
        .append("&redirect_uri=")
        .append(redirectUrl)
        .append("&response_type=code&scope=")
        .append((quiet ? AuthType.BASE.scope() : AuthType.USER_INFO.scope()))
        .append("&state=")
        .append(state)
        .append("#wechat_redirect")
        .toString();
    } catch (UnsupportedEncodingException e) {
      throw new WechatException(e);
    }
  }

  /**
   * {
   * "access_token":"ACCESS_TOKEN",
   * "expires_in":7200,
   * "refresh_token":"REFRESH_TOKEN",
   * "openid":"OPENID",
   * "scope":"SCOPE"
   * }
   */
  public Map<String, Object> refreshToken(final String refreshToken) {
    checkNotBlank(refreshToken);
    String url = AUTH_ACCESS_TOKEN_REFRESH +
      "appid=" + wechat.getAppId() +
      "&grant_type=refresh_token" +
      "&refresh_token=" + refreshToken;
    return doGet(url);
  }

  /**
   * 获取用户openId
   *
   * @param code 用户授权的code
   * @return 用户的openId，或抛WechatException
   */
  public String openId(String code) {
    checkNotBlank(code);
    String url = OPEN_ID_URL +
      "appid=" + wechat.getAppId() +
      "&secret=" + wechat.getAppSecret() +
      "&code=" + code +
      "&grant_type=authorization_code";

    Map<String, Object> resp = doGet(url);

    return (String) resp.get("openid");
  }

  /**
   * 获取用户openId accessToken
   */
  public Map<String, Object> getOpenIdMap(String code) {
    checkNotBlank(code);
    String url = OPEN_ID_URL +
      "appid=" + wechat.getAppId() +
      "&secret=" + wechat.getAppSecret() +
      "&code=" + code +
      "&grant_type=authorization_code";
    return doGet(url);
  }

  /**
   * 获取accessToken(应该尽量临时保存一个地方，每隔一段时间来获取)
   *
   * @return accessToken，或抛WechatException
   */
  public AccessToken accessToken() {
    String url = ACCESS_TOKEN_URL + "&appid=" + wechat.getAppId() + "&secret=" + wechat.getAppSecret();

    Map<String, Object> resp = doGet(url);
    AccessToken token = new AccessToken();
    token.setAccessToken((String) resp.get("access_token"));
    Integer expire = (Integer) resp.get("expires_in");
    token.setExpire(expire);
    token.setExpiredAt(System.currentTimeMillis() + expire * 1000);

    return token;
  }

}
