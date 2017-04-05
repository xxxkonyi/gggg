package com.believe.wechat;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;


/**
 * @author Lxp
 */
public class QyBases extends Component {

  /**
   * 获取接口访问AccessToken
   */
  private static final String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?";

  /**
   * 授权
   */
  private static final String AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?";

  /**
   * 获取用户userID
   */
  private static final String USER_ID_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?";

  /**
   * userId 转换 OpenID
   */
  private static final String USERID_TO_OPENID = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?";

  /**
   * openId 转换 userId
   */
  private static final String OPENID_TO_USERID = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?";

  QyBases() {
  }

  /**
   * 获取accessToken(应该尽量临时保存一个地方，每隔一段时间来获取)
   *
   * @param cb 回调
   */
  public void accessToken(final Callback<AccessToken> cb) {
    doAsync(new AsyncFunction<AccessToken>(cb) {
      @Override
      public AccessToken execute() {
        return accessToken();
      }
    });
  }

  /**
   * 获取accessToken(应该尽量临时保存一个地方，每隔一段时间来获取)
   *
   * @return accessToken，或抛WechatException
   */
  public AccessToken accessToken() {
    String url = ACCESS_TOKEN_URL + "&corpid=" + wechat.getAppId() + "&corpsecret=" + wechat.getAppSecret();

    Map<String, Object> resp = doGet(url);
    AccessToken token = new AccessToken();
    token.setAccessToken((String) resp.get("access_token"));
    Integer expire = (Integer) resp.get("expires_in");
    token.setExpire(expire);
    token.setExpiredAt(System.currentTimeMillis() + expire * 1000);

    return token;
  }

  /**
   * 构建授权跳转URL
   *
   * @param redirectUrl 授权后的跳转URL(我方服务器URL)
   * @param userId      重定向后会带上state参数，企业可以填写a-zA-Z0-9的参数值，长度不可超过128个字节
   * @return 微信授权跳转URL
   */
  public String authUrl(String redirectUrl, String userId) {
    try {
      checkNotNullAndEmpty(redirectUrl, "redirectUrl");
      redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
      return AUTH_URL +
        "appid=" + wechat.getAppId() +
        "&redirect_uri=" + redirectUrl +
        "&response_type=code&scope=" +
        AuthType.BASE.scope()
        + "&state=" + userId + "#wechat_redirect";
    } catch (UnsupportedEncodingException e) {
      throw new WechatException(e);
    }
  }

  /**
   * 获取企业用户UserId或者非企业用户OpenID
   *
   * @param code 微信返回Code
   * @return UserId Or openId
   */
  public String userIdOrOpenId(final String code) {
    Map<String, Object> resp = getIdByCode(code);
    return (String) (resp.get("UserId") != null ? resp.get("UserId") : resp.get("OpenId"));
  }

  /**
   * 获取企业用户UserId或者非企业用户OpenID
   *
   * @param code 微信返回Code
   * @return UserId Or openId
   */
  public String openId(final String code) {
    Map<String, Object> resp = getIdByCode(code);
    String userId = (String) resp.get("UserId");
    String openId = (String) resp.get("OpenId");
    if (StringUtils.isBlank(openId)) {
      openId = useridToOpenid(userId, null);
    }
    return openId;
  }

  private Map<String, Object> getIdByCode(final String code) {
    checkNotNullAndEmpty(code, "code");
    String url = USER_ID_URL +
      "access_token=" + loadQyAccessToken() +
      "&code=" + code;
    return doGet(url);
  }

  public String getQyAccessToken() {
    return this.loadQyAccessToken();
  }

  /**
   * 该接口使用场景为微信支付、微信红包和企业转账，企业号用户在使用微信支付的功能时，需要自行将企业号的userid转成openid。
   * 在使用微信红包功能时，需要将应用id和userid转成appid和openid才能使用。
   *
   * @param userid  企业号内的成员id
   * @param agentid 整型，需要发送红包的应用ID，若只是使用微信支付和企业转账，则无需该参数
   * @return OpenId
   */
  public String useridToOpenid(String userid, String agentid) {
    checkNotNullAndEmpty(userid, "userid");
    Map<String, Object> params = com.google.common.collect.Maps.newHashMap();
    String url = USERID_TO_OPENID + "access_token=" + loadAccessToken();
    params.put("userid", userid);
    if (null != agentid) {
      params.put("agentid", agentid);
    }
    Map<String, Object> resp = doPost(url, params);
    return (String) resp.get("openid");
  }

  /**
   * 该接口主要应用于使用微信支付、微信红包和企业转账之后的结果查询，
   * 开发者需要知道某个结果事件的openid对应企业号内成员的信息时，可以通过调用该接口进行转换查询
   *
   * @param openid 企业号内的成员id
   * @return userid 该openid在企业号中对应的成员userid
   */
  public String openidToUserid(String openid) {
    checkNotNullAndEmpty(openid, "openid");
    Map<String, Object> params = com.google.common.collect.Maps.newHashMap();
    String url = OPENID_TO_USERID + "access_token=" + loadAccessToken();
    params.put("openid", openid);
    Map<String, Object> resp = doPost(url, params);
    return (String) resp.get("userid");
  }

}
