package com.believe.website;

import com.believe.core.domain.Customer;
import com.believe.core.domain.CustomerAddress;
import com.believe.core.domain.Manifesto;
import com.believe.core.service.CustomerService;
import com.believe.core.service.ManifestoService;
import com.believe.core.service.dto.PraiseResult;
import com.believe.core.service.impl.WechatSupport;
import com.believe.utils.SessionUtils;
import com.believe.website.dto.ManifestoDto;
import com.believe.website.dto.PraiseDto;
import com.believe.wechat.model.Config;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

  @Autowired
  private WechatSupport wechatSupport;

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
    if (StringUtils.isBlank(dto.getRemark()) || dto.getRemark().length() < 10) {
      model.addAttribute("errorMsg", "请输入不少于10个字的宣言!");
      return "index";
    }
    Manifesto manifesto = manifestoService.createManifesto(customer, dto.getRemark());
    return redirectPerson(customer.getOpenId());
  }

  /* 用户收货地址 */
  @RequestMapping(value = "/address")
  public String addressView(@RequestParam(required = false, value = "uid") String uid, Model model) {
    Customer creator = SessionUtils.getCurrentUser();
    if (customerService.beforeExistAddress(creator.getOpenId())) {
      return redirectPerson(creator.getOpenId());
    }
    Manifesto manifesto = manifestoService.getByIdentify(creator.getOpenId());
    model.addAttribute("manifesto", manifesto);
    return "address";
  }

  @RequestMapping(value = "/address", method = RequestMethod.POST)
  public String address(CustomerAddress address, Model model) {
    Customer creator = SessionUtils.getCurrentUser();
    if (StringUtils.isBlank(address.getMobilePhone())) {
      model.addAttribute("errorMsg", "手机号不能为空");
      return "address";
    }
    if (StringUtils.isBlank(address.getRealName())) {
      model.addAttribute("errorMsg", "姓名不能为空");
      return "address";
    }
    if (StringUtils.isBlank(address.getAddress())) {
      model.addAttribute("errorMsg", "地址不能为空");
      return "address";
    }
    customerService.createAddress(address, creator);
    return redirectPerson(creator.getOpenId());
  }

  /* 我的宣言 */
  @RequestMapping(value = "/person")
  public String person(@RequestParam(required = false, value = "uid") String uid, Model model) {
    Customer customer = SessionUtils.getCurrentUser();
    boolean isSelf = StringUtils.isBlank(uid) || uid.equals(customer.getOpenId());
    Manifesto manifesto = null;
    if (isSelf) {
      manifesto = manifestoService.getByIdentify(customer.getOpenId());
      if (null == manifesto) {
        // 我的宣言
        return "redirect:/auth/index";
      }
      build(manifesto.isWined(), customerService.beforeExistAddress(customer.getOpenId()), true, model);
    } else {
      manifesto = manifestoService.getByIdentify(uid);
      if (null == manifesto) {
        // 全部宣言
        return "redirect:/auth/declaration";
      }
      build(false, false, false, model);
    }

    Config config = wechatSupport.jsConfig(StringUtils.isBlank(uid) ? "auth/person" : "auth/person?uid=" + uid);
    model.addAttribute("uid", StringUtils.isBlank(uid) ? customer.getOpenId() : uid);
    model.addAttribute("config", config);
    model.addAttribute("totalManifesto", manifestoService.countManifesto());
    model.addAttribute("manifesto", ManifestoDto.of(manifesto));
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
  ResponseEntity<PraiseResult> praise(@RequestBody PraiseDto dto) {
    Customer customer = SessionUtils.getCurrentUser();
    PraiseResult result = manifestoService.praiseManifesto(dto.getManifestoId(), customer.getId());
    return ResponseEntity.ok(result);
  }

  /*  全部宣言列表 */
  @RequestMapping(value = "/declaration")
  public String declarationView(@PageableDefault(page = 0, size = 40,
    direction = Sort.Direction.DESC,
    sort = {"createdDate"}) Pageable pageable, Model model) {

    Page<Manifesto> manifestoPage = manifestoService.findAll(pageable);
    model.addAttribute("manifestoPage", manifestoPage);
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
