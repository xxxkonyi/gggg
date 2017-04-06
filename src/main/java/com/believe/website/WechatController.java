package com.believe.website;

import com.believe.core.domain.Customer;
import com.believe.core.service.CustomerService;
import com.believe.core.service.impl.WechatSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 17:56
 * @since 1.0
 */
@Slf4j
@RequestMapping(value = "/api")
public class WechatController {

  @Autowired
  private WechatSupport wechatSupport;
  @Autowired
  private CustomerService customerService;

  /**
   * 认证回调
   */
  @RequestMapping(value = "/notifies/auth", method = RequestMethod.GET)
  public void wxAuthNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String userId = request.getParameter("state");
    String code = request.getParameter("code");
    String openId = wechatSupport.bases().openId(code);
    Customer customer = customerService.get(userId);
    customer.setOpenId(openId);
    try {
      response.sendRedirect("");
    } catch (IOException e) {
      throw e;
    }
  }

}
