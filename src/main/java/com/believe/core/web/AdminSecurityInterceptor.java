package com.believe.core.web;

import com.believe.core.domain.Admin;
import com.believe.core.service.impl.AdminServiceImpl;
import com.believe.utils.SessionUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/8 15:56
 * @since 1.0
 */
@Slf4j
public class AdminSecurityInterceptor extends HandlerInterceptorAdapter {

  @Setter
  private AdminServiceImpl adminService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String requestURI = request.getRequestURI();
    log.info("Current admin request uri {} ", requestURI);
    if ("/admin/login".equals(requestURI) && "GET".equals(request.getMethod())) {
      return true;
    }
    if ("/admin/login".equals(requestURI) && "POST".equals(request.getMethod())) {
      String username = request.getParameter("username");
      String password = request.getParameter("password");
      SessionUtils.setCurrentAdmin(adminService.login(username, password));
      response.sendRedirect("/admin/index");
      return true;
    }
    if ("/admin/logout".equals(requestURI) && "POST".equals(request.getMethod())) {
      SessionUtils.remove(Admin.class);
      response.sendRedirect("/admin/login");
      return false;
    }
    if (null == SessionUtils.getCurrentAdmin()) {
      response.sendRedirect("/admin/login");
      return false;
    }
    return true;
  }

}
