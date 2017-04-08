package com.believe.admin;

import com.believe.core.domain.Admin;
import com.believe.core.domain.CustomerAddress;
import com.believe.core.repository.CustomerAddressRepository;
import com.believe.utils.SessionUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
    SessionUtils.remove(Admin.class);
    return "redirect:/admin/login";
  }

  @RequestMapping(value = {"/index", "/"})
  public String index(Pageable pageable, Model model) {
    Page<CustomerAddress> customerAddressesPage = customerAddressRepository.findAll(pageable);
    model.addAttribute("customerAddressPage", customerAddressesPage);
    customerAddressesPage.getTotalPages();
    List<Integer> pages = Lists.newArrayList();
    for (int i = 0; i < customerAddressesPage.getTotalPages(); i++) {
      pages.add(i);
    }
    model.addAttribute("pages", pages);
    model.addAttribute("currentPage", pageable.getPageNumber());
    return "admin/index";
  }

  @Autowired
  private CustomerAddressRepository customerAddressRepository;

}
