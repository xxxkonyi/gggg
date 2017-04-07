package com.believe.website;

import com.believe.core.domain.Customer;
import com.believe.core.domain.CustomerAddress;
import com.believe.core.domain.Manifesto;
import com.believe.core.service.CustomerService;
import com.believe.core.service.ManifestoService;
import com.believe.utils.SessionUtils;
import com.believe.website.dto.ManifestoDto;
import com.believe.website.dto.PraiseDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
  @RequestMapping(value = "/index")
  public String manifestoView(@RequestParam(required = false, value = "uid") String uid, Model model) {
    Customer customer = SessionUtils.getCurrentUser();
    if (manifestoService.beforeExist(customer.getOpenId())) {
      return redirectPerson(customer.getOpenId());
    }
    model.addAttribute("totalManifesto", manifestoService.countManifesto());
    return "index";
  }

  @RequestMapping(value = "/index", method = RequestMethod.POST)
  public String manifesto(ManifestoDto dto, Model model) {
    Customer customer = SessionUtils.getCurrentUser();
    Manifesto manifesto = manifestoService.createManifesto(customer, dto.getRemark());
    return redirectPerson(customer.getOpenId());
  }

  /* 用户收货地址 */
  @RequestMapping(value = "/address")
  public String addressView(@RequestParam(required = false, value = "uid") String uid, Model model) {
    Customer creator = SessionUtils.getCurrentUser();
    Manifesto manifesto = manifestoService.getByIdentify(creator.getOpenId());
    model.addAttribute("manifesto", manifesto);
    return "address";
  }

  @RequestMapping(value = "/address", method = RequestMethod.POST)
  public String address(CustomerAddress address, Model model) {
    Customer creator = SessionUtils.getCurrentUser();
    customerService.createAddress(address, creator);
    return redirectPerson(creator.getOpenId());
  }

  /* 我的宣言 */
  @RequestMapping(value = "/person")
  public String person(@RequestParam(required = false, value = "uid") String uid, Model model) {
    Customer customer = SessionUtils.getCurrentUser();
    Manifesto manifesto = null;
    if (StringUtils.isNotBlank(uid)) {
      manifesto = manifestoService.getByIdentify(uid);
    } else {
      manifesto = manifestoService.getByIdentify(customer.getOpenId());
    }
    ManifestoDto dto = null;
    model.addAttribute("totalManifesto", manifestoService.countManifesto());
    if (manifesto == null) {
      build(false, false, true, model);
      dto = ManifestoDto.of(customer);
    } else {
      build(manifesto.isWined(), manifestoService.beforeExist(customer.getOpenId()), manifesto.getOpenId().equals(customer.getOpenId()), model);
      dto = ManifestoDto.of(manifesto);
    }
    model.addAttribute("manifesto", dto);
    return "person";
  }

  private Model build(Boolean isWined, Boolean existAddress, Boolean isSelf, Model model) {
    model.addAttribute("isSelf", isSelf);
    model.addAttribute("isWined", isWined);
    model.addAttribute("existAddress", existAddress);
    return model;
  }

  /*  用户点赞 */
  @RequestMapping(value = "/praise")
  public
  @ResponseBody
  ResponseEntity<Void> praise(@RequestBody PraiseDto dto) {
    Customer customer = SessionUtils.getCurrentUser();
    manifestoService.praiseManifesto(dto.getManifestoId(), customer.getId());
    return ResponseEntity.ok(null);
  }

  /*  全部宣言列表 */
  @RequestMapping(value = "/declaration")
  public String declarationView() {
    return "declaration";
  }

  private String redirectPerson(String externalUid) {
    StringBuilder sb = new StringBuilder("redirect:/auth/person");
    if (StringUtils.isNotBlank(externalUid)) {
      sb.append("?uid=").append(externalUid);
    }
    return sb.toString();
  }

}
