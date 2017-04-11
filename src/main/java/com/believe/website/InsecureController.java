package com.believe.website;

import com.believe.core.service.impl.WechatSupport;
import com.believe.wechat.model.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

  @Autowired
  private WechatSupport wechatSupport;

  /* 我也要宣言 */
  @RequestMapping(value = "/introduce")
  public String introduce(Model model) {
    Config config = wechatSupport.jsConfig("introduce");
    model.addAttribute("config", config);
    return "introduce";
  }

  @RequestMapping(value = "/general")
  public String error() {
    return "general";
  }

}
