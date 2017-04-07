package com.believe.core.web;

import com.believe.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String requestURI = request.getRequestURI();
    System.out.println(String.format("Current request uri %s ", requestURI));
    if (!SessionUtils.isAuthenticated()) {
      response.sendRedirect("/wx" + requestURI);
      return false;
    }
    return true;
  }

}
