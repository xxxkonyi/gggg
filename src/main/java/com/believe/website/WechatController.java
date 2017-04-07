package com.believe.website;

import com.believe.core.constant.SystemConstant;
import com.believe.core.domain.Customer;
import com.believe.core.service.CustomerService;
import com.believe.core.service.impl.WechatSupport;
import com.believe.utils.SessionUtils;
import com.believe.wechat.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 17:56
 * @since 1.0
 */
@Slf4j
@Controller
@RequestMapping(value = "/wx")
public class WechatController {

  @Autowired
  private WechatSupport wechatSupport;
  @Autowired
  private CustomerService customerService;

  @RequestMapping(value = "/auth/{path}")
  public String authRequest(@PathVariable String path, @RequestParam(required = false, value = "uid") String uid, HttpServletRequest request, Model model) {
    log.info("Auth request url {} ", path);
    if (!SystemConstant.AUTH_URL.contains(path)) {
      return "general";
    }
    if (StringUtils.isNotBlank(uid)) {
      request.getSession().setAttribute("uid", uid);
    }
    String authUrl = wechatSupport.authUrl(path);
    model.addAttribute("auth_url", authUrl);
    return "auth";
  }

  /**
   * 认证回调
   */
  @RequestMapping(value = "/notifies/auth", method = RequestMethod.GET)
  public void wxAuthNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String state = request.getParameter("state");
    String code = request.getParameter("code");

    log.info("Get code {} | state {}", code, state);

    if (StringUtils.isBlank(code)) {
      log.warn("Wx return code is null");
      error(response);
      return;
    }

    Map<String, Object> resultMap = wechatSupport.bases().getOpenIdMap(code);
    String openid = (String) resultMap.get("openid");
    String access_token = (String) resultMap.get("access_token");

    log.info("Get openid {} | access_token {}", openid, access_token);

    if (StringUtils.isBlank(openid) || StringUtils.isBlank(access_token)) {
      log.warn("Wx return openid | access_token is null");
      error(response);
      return;
    }

    User user = wechatSupport.users().getSnsUser(access_token, openid);

    if (user == null) {
      log.warn("Wx return user info is null");
      error(response);
      return;
    }
    log.info("Get user info {} ", user.toString());
    Customer customer = customerService.updateCustomer(openid, user);
    SessionUtils.setCurrentUser(customer);
    try {
      String redirectUrl = "/auth/" + state;
      if (null != request.getSession().getAttribute("uid")) {
        redirectUrl += "?uid=" + request.getSession().getAttribute("uid");
        request.getSession().removeAttribute("uid");
      }
      response.sendRedirect(redirectUrl);
    } catch (IOException e) {
      throw e;
    }
  }

  public void error(HttpServletResponse response) {
    try {
      response.sendRedirect("/general");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
