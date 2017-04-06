package com.believe.core.web;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 22:57
 * @since 1.0
 */
public class SessionTokenFactory implements TokenFactory<String> {

  private TokenManger<String> cache = new TokenManger<String>() {

    private Map<String, Token<String>> tokens = Maps.newConcurrentMap();

    @Override
    public Token<String> get(String key) {
      return tokens.get(key);
    }

    @Override
    public Token<String> put(Token<String> token) {
      return tokens.put(token.getKey(), token);
    }

    @Override
    public boolean remove(String key) {
      return null != tokens.remove(key);
    }
  };

  @Override
  public Token<String> createToken(String key) {
    SessionToken token = new SessionToken(key);
    cache.put(token);
    return token;
  }

  @Override
  public Token<String> get(String key) {
    return cache.get(key);
  }

  @Override
  public boolean remove(String key) {
    return cache.remove(key);
  }

}
