package com.believe.admin;

import com.believe.core.domain.Admin;
import com.believe.core.domain.CustomerAddress;
import com.believe.core.repository.CustomerAddressRepository;
import com.believe.utils.ExcelUtils;
import com.believe.utils.SessionUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

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
  public String index(@RequestParam(required = false, value = "queryInfo") String queryInfo, @PageableDefault(value = 50, page = 0) Pageable pageable, Model model) {
    Page<CustomerAddress> customerAddressesPage;
    if (StringUtils.isBlank(queryInfo)) {
      customerAddressesPage = customerAddressRepository.findAll(pageable);
    } else {
      customerAddressesPage = customerAddressRepository.findByMobilePhone(StringUtils.trim(queryInfo), pageable);
    }
    model.addAttribute("customerAddressPage", customerAddressesPage);
    model.addAttribute("queryInfo", queryInfo);
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

  @RequestMapping("export")
  public void export(HttpServletResponse response) {
    String[] titles = {"姓名", "手机号", "地址", "创建时间"};
    List contents = customerAddressRepository.findAll().stream().map(AddressDto::of).collect(Collectors.toList());
    ExcelUtils.export("中奖名单.xls", titles, contents, response);
  }

}
