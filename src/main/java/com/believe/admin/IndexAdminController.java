package com.believe.admin;

import com.believe.core.repository.CustomerAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

  @Autowired
  private CustomerAddressRepository customerAddressRepository;

  @RequestMapping(value = "/customer_address")
  public String customerAddress(Pageable pageable, Model model) {
    model.addAttribute("customerAddressPage", customerAddressRepository.findAll(pageable));
    return "admin/customer_address";
  }

}
