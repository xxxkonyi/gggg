package com.believe.core.web;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 22:36
 * @since 1.0
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


    return true;
  }

}
