package com.believe.website;

import com.believe.core.service.CustomerService;
import com.believe.core.service.ManifestoService;
import com.believe.website.dto.ManifestoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.believe.utils.ResponseUtils.success;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 22:41
 * @since 1.0
 */
@RestController
@RequestMapping("/api")
public class ManifestoApiController {

  @Autowired
  private CustomerService customerService;

  @Autowired
  private ManifestoService manifestoService;

  @RequestMapping(value = "/manifesto", method = RequestMethod.POST)
  public ResponseEntity manifesto(@RequestBody ManifestoDto dto) {
//    manifestoService.createManifesto(dto.getRemark());
    return success();
  }

  /*宣言总数*/
  @RequestMapping(value = "/mf_count")
  public ResponseEntity<Long> countManifesto() {
    return success(manifestoService.countManifesto());
  }

}
