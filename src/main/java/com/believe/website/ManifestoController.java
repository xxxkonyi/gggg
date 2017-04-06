package com.believe.website;

import com.believe.core.domain.Customer;
import com.believe.core.domain.CustomerAddress;
import com.believe.core.service.CustomerService;
import com.believe.core.service.ManifestoService;
import com.believe.utils.SessionUtils;
import com.believe.website.dto.ManifestoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 22:41
 * @since 1.0
 */
@Controller
@RequestMapping("/auth")
public class ManifestoController {

  @Autowired
  private CustomerService customerService;

  @Autowired
  private ManifestoService manifestoService;

  /* 发起宣言 */
  @RequestMapping(value = "/")
  public String manifestoView(Model model) {
    model.addAttribute("totalManifesto", manifestoService.countManifesto());
    return "index";
  }

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public String manifesto(ManifestoDto dto) {
    Customer customer = SessionUtils.getCurrentUser();
    // // TODO: 2017/4/6  
    manifestoService.createManifesto(customer.getId(), customer.getOpenId(), dto.getRemark());
    return "index";
  }

  /* 用户收货地址 */
  @RequestMapping(value = "/address")
  public String addressView() {
    return "address";
  }

  @RequestMapping(value = "/address", method = RequestMethod.POST)
  public String address(CustomerAddress address) {
    // // TODO: 2017/4/6 获取当前用户
    customerService.createAddress(address);
    return "address";
  }

  /*  全部宣言列表 */
  @RequestMapping(value = "/declaration")
  public String declarationView() {
    return "declaration";
  }

  /* 我的宣言 */
  @RequestMapping(value = "/person")
  public String person() {
    return "person";
  }

}
