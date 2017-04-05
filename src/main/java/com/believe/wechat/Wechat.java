package com.believe.wechat;

import com.believe.exception.WechatException;
import com.believe.utils.Fields;
import com.believe.utils.http.Http;
import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.AsyncFunction;
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
   * 异步执行器
   */
  ExecutorService executor = DEFAULT_EXECUTOR;

  /**
   * 微信错误码变量
   */
  private final String ERROR_CODE = "errcode";

  private static final String BASES = "com.believe.shop.wechat.core.user.Bases";

  private static final String QY_BASES = "com.believe.shop.wechat.core.user.QyBases";

  private static final String USERS = "com.believe.shop.wechat.core.user.Users";

  private static final String MENUS = "com.believe.shop.wechat.core.Menus";

  private static final String CUSTOMER_SERVICES = "com.believe.shop.wechat.core.user.CustomerServices";

  private static final String MESSAGES = "com.believe.shop.wechat.core.user.Messages";

  private static final String QRCODES = "com.believe.shop.wechat.core.user.QrCodes";

  private static final String MATERIALS = "com.believe.shop.wechat.core.user.Materials";

  private static final String DATAS = "com.believe.shop.wechat.core.user.Datas";

  private static final String JSSDKS = "com.believe.shop.wechat.core.user.JsSdks";

  private static final AccessTokenLoader DEFAULT_ACCESS_TOKEN_LOADER = new DefaultAccessTokenLoader();

  private static final JavaType MAP_STRING_OBJ_TYPE = JsonUtils.DEFAULT.createCollectionType(Map.class, String.class, Object.class);

  private static final ExecutorService DEFAULT_EXECUTOR = Executors.newFixedThreadPool(
    Runtime.getRuntime().availableProcessors() + 1, new ThreadFactory() {
      @Override
      public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName("wechat");
        return t;
      }
    });

  Wechat(String appId, String appSecret) {
    this.appId = appId;
    this.appSecret = appSecret;
  }

  public String getAppId() {
    return appId;
  }

  public String getAppSecret() {
    return appSecret;
  }

  public String getAppToken() {
    return appToken;
  }

  public String getMsgKey() {
    return msgKey;
  }

  private void injectWechat(Class clazz, Object comp) throws NoSuchFieldException {
    Field wechat = clazz.getSuperclass().getDeclaredField("wechat");
    Fields.put(comp, wechat, this);
  }

  /**
   * 注册组件
   *
   * @param component 组件对象
   * @param <T>       范型
   */
  public <T extends Component> void register(T component) {
    try {
      injectWechat(component.getClass(), component);
    } catch (NoSuchFieldException e) {
      throw new WechatException(e);
    }
  }

  /**
   * 关闭异步执行器(不再支持异步执行)
   */
  public void destroy() {
    if (executor.isShutdown()) {
      executor.shutdown();
    }
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

  String loadQyAccessToken() {
    String accessToken = tokenLoader.get();
    if (Strings.isNullOrEmpty(accessToken)) {
      AccessToken token = qyBases().accessToken();
      tokenLoader.refresh(token);
      accessToken = token.getAccessToken();
    }
    return accessToken;
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

  <T> void doAsync(final AsyncFunction<T> f) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        try {
          T res = f.execute();
          f.cb.onSuccess(res);
        } catch (Exception e) {
          f.cb.onFailure(e);
        }
      }
    });
  }
}
