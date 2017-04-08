package com.believe.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 22:41
 * @since 1.0
 */
@Controller
@RequestMapping(value = "admin")
public class IndexAdminController {

  @RequestMapping("login")
  public String login() {
    return "admin/login";
  }

  @RequestMapping("logout")
  public String logout() {
    return "redirect:/admin/login";
  }

  @RequestMapping(value = {"/index", "/"})
  public String index() {
    return "admin/index";
  }

}
