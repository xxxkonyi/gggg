package com.believe.wechat;

import com.believe.wechat.model.Config;
import com.believe.wechat.model.Ticket;
import com.believe.wechat.model.TicketType;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import java.util.Map;

import static com.believe.utils.PreconditionUtils.checkNotBlank;
import static com.believe.utils.PreconditionUtils.checkNotNull;

public final class JsSdks extends Component {

  /**
   * 获取Ticket
   */
  private static final String TICKET_GET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=";

  public Ticket getTicket(TicketType type) {
    return getTicket(loadAccessToken(), type);
  }

  public Ticket getTicket(String accessToken, TicketType type) {
    checkNotBlank(accessToken);
    checkNotNull(type);

    String url = TICKET_GET + accessToken + "&type=" + type.type();
    Map<String, Object> resp = doGet(url);
    Ticket t = new Ticket();
    t.setTicket((String) resp.get("ticket"));
    Integer expire = (Integer) resp.get("expires_in");
    t.setExpire(expire);
    t.setExpireAt(System.currentTimeMillis() + expire * 1000);
    t.setType(type);
    return t;
  }

  public Config getConfig(String nonStr, String url) {
    return getConfig(wechat.loadTicket(TicketType.JSAPI), nonStr, url);
  }

  public Config getConfig(String jsApiTicket, String nonStr, String url) {
    return getConfig(jsApiTicket, nonStr, System.currentTimeMillis() / 1000, url);
  }

  public Config getConfig(String nonStr, Long timestamp, String url) {
    return getConfig(wechat.loadTicket(TicketType.JSAPI), nonStr, timestamp, url);
  }

  public Config getConfig(String jsApiTicket, String nonStr, Long timestamp, String url) {
    checkNotBlank(jsApiTicket);
    checkNotBlank(nonStr);
    checkNotNull(timestamp);
    String signStr = "jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s";
    signStr = String.format(signStr, jsApiTicket, nonStr, timestamp, url);
    String sign = Hashing.sha1().hashString(signStr, Charsets.UTF_8).toString().toLowerCase();
    return new Config(wechat.getAppId(), timestamp, nonStr, sign);
  }

}
