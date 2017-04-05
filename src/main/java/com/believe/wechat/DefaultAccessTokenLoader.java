package com.believe.wechat;

import com.google.common.base.Strings;

/**
 * 一个内存式AccessToken加载器(生产环境不推荐使用)
 */
public class DefaultAccessTokenLoader implements AccessTokenLoader {

  private volatile AccessToken validToken;

  @Override
  public String get() {
    return (validToken == null
      || Strings.isNullOrEmpty(validToken.getAccessToken())
      || System.currentTimeMillis() > validToken.getExpiredAt()) ? null : validToken.getAccessToken();
  }

  @Override
  public void refresh(AccessToken token) {
    validToken = token;
  }
}
