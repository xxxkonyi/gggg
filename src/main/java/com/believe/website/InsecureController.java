package com.believe.website;

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

}
