package com.believe.website;

import com.believe.wechat.model.Config;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 22:41
 * @since 1.0
 */
@Controller
public class InsecureController {

  /* 我也要宣言 */
  @RequestMapping(value = "/introduce")
  public String introduce() {
    return "introduce";
  }

  @RequestMapping(value = "/general")
  public String error() {
    return "general";
  }

  @RequestMapping(value = "/config")
  public @ResponseBody Config getJsSdksConfig(HttpServletRequest request) {
    request.getRequestURL();
    System.out.println(request.getRequestURI());
    System.out.println(request.getRequestURI());
//    return wechatSupport.jsConfig()
    return null;
  }

}
