package com.believe.wechat;

import com.believe.exception.WechatException;
import com.believe.utils.Fields;
import com.believe.utils.http.Http;
import com.believe.wechat.model.Ticket;
import com.believe.wechat.model.TicketType;
import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Getter;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 微信核心组件库
 */
@Getter
public final class Wechat {

  /**
   * 微信APP ID
   */
  private String appId;

  /**
   * 微信APP 密钥
   */
  private String appSecret;

  /**
   * 微信APP (令牌)Token
   */
  String appToken;

  /**
   * 消息加密Key
   */
  String msgKey;

  /**
   * AccessToken加载器
   */
  AccessTokenLoader tokenLoader = DEFAULT_ACCESS_TOKEN_LOADER;

  /**
   * Ticket加载器
   */
  TicketLoader ticketLoader = DEFAULT_TICKET_LOADER;

  /**
   * 微信错误码变量
   */
  private final String ERROR_CODE = "errcode";

  private static final String BASES = "com.believe.wechat.Bases";
  private static final String USERS = "com.believe.wechat.Users";
  private static final String MESSAGES = "com.believe.wechat.Messages";
  private static final String JSSDKS = "com.believe.wechat.JsSdks";

  private static final AccessTokenLoader DEFAULT_ACCESS_TOKEN_LOADER = new DefaultAccessTokenLoader();
  private static final DefaultTicketLoader DEFAULT_TICKET_LOADER = new DefaultTicketLoader();

  private static final JavaType MAP_STRING_OBJ_TYPE = JsonUtils.DEFAULT.createCollectionType(Map.class, String.class, Object.class);

  public Bases base() {
    return (Bases) components.getUnchecked(BASES);
  }

  public Users users() {
    return (Users) components.getUnchecked(USERS);
  }

  public Messages messages() {
    return (Messages) components.getUnchecked(MESSAGES);
  }

  public JsSdks js() {
    return (JsSdks) components.getUnchecked(JSSDKS);
  }

  Wechat(String appId, String appSecret) {
    this.appId = appId;
    this.appSecret = appSecret;
  }

  private LoadingCache<String, Component> components =
    CacheBuilder.newBuilder().build(new CacheLoader<String, Component>() {
      @Override
      public Component load(String classFullName) throws Exception {
        Class clazz = Class.forName(classFullName);
        Object comp = clazz.newInstance();
        injectWechat(clazz, comp);
        return (Component) comp;
      }
    });

  private void injectWechat(Class clazz, Object comp) throws NoSuchFieldException {
    Field wechat = clazz.getSuperclass().getDeclaredField("wechat");
    Fields.put(comp, wechat, this);
  }

  String loadAccessToken() {
    String accessToken = tokenLoader.get();
    if (Strings.isNullOrEmpty(accessToken)) {
      AccessToken token = base().accessToken();
      tokenLoader.refresh(token);
      accessToken = token.getAccessToken();
    }
    return accessToken;
  }

  String loadTicket(TicketType type) {
    String ticket = ticketLoader.get(type);
    if (Strings.isNullOrEmpty(ticket)) {
      Ticket t = js().getTicket(type);
      ticketLoader.refresh(t);
      ticket = t.getTicket();
    }
    return ticket;
  }

  Map<String, Object> doPost(String url, Map<String, Object> params) {
    String body = null;
    if (params != null && !params.isEmpty()) {
      body = JsonUtils.DEFAULT.toJson(params);
    }
    return doPost(url, body);
  }

  Map<String, Object> doPost(String url, String body) {
    Http http = Http.post(url);
    if (!Strings.isNullOrEmpty(body)) {
      http.body(body);
    }
    Map<String, Object> resp = http.request(MAP_STRING_OBJ_TYPE);
    Integer errcode = (Integer) resp.get(ERROR_CODE);
    if (errcode != null && errcode != 0) {
      throw new WechatException(resp);
    }
    return resp;
  }

  Map<String, Object> doGet(String url) {
    return doGet(url, null);
  }

  Map<String, Object> doGet(String url, Map<String, Object> params) {
    Http http = Http.get(url);
    if (params != null && params.size() > 0) {
      http.body(JsonUtils.DEFAULT.toJson(params));
    }
    Map<String, Object> resp = http.request(MAP_STRING_OBJ_TYPE);
    Integer errcode = (Integer) resp.get(ERROR_CODE);
    if (errcode != null && errcode != 0) {
      throw new WechatException(resp);
    }
    return resp;
  }

  Map<String, Object> doUpload(String url, String fieldName, String fileName, InputStream input, Map<String, String> params) {
    String json = Http.upload(url, fieldName, fileName, input, params);
    Map<String, Object> resp = JsonUtils.DEFAULT.fromJson(json, MAP_STRING_OBJ_TYPE);
    Integer errcode = (Integer) resp.get(ERROR_CODE);
    if (errcode != null && errcode != 0) {
      throw new WechatException(resp);
    }
    return resp;
  }
}
