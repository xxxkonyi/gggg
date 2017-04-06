package com.believe.website;

import com.believe.core.service.CustomerService;
import com.believe.website.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 22:41
 * @since 1.0
 */
@Controller
public class IndexController {

  @RequestMapping(value = "/")
  public String index() {
    return "index";
  }

  @Autowired
  private CustomerService customerService;

  @RequestMapping(value = "/api/customer")
  public
  @ResponseBody
  String customer(@RequestBody CustomerDto dto) {
    customerService.createCustomer(dto.getNickName());
    return "ok";
  }

}
