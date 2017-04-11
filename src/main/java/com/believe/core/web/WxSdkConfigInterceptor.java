package com.believe.core.web;

import com.believe.core.service.impl.WechatSupport;
import com.believe.wechat.model.Config;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
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
public class WxSdkConfigInterceptor extends HandlerInterceptorAdapter {

  @Setter
  private WechatSupport wechatSupport;

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    if ("GET".equals(request.getMethod())) {
      String requestURI = request.getRequestURI();
      String query = request.getQueryString();
      log.info("WxSdk config request uri {} ", requestURI);
      if (StringUtils.isNotBlank(query)) {
        requestURI += "?" + query;
      }
      Config config = wechatSupport.jsConfig(requestURI);
      modelAndView.addObject("config", config);
    }
  }
}
