package com.believe.core.service.impl;

import com.believe.core.domain.Admin;
import com.believe.core.repository.AdminRepository;
import com.believe.exception.ResponseException;
import com.believe.exception.ResponseViewException;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/8 16:07
 * @since 1.0
 */
@Service
public class AdminServiceImpl implements InitializingBean {

  @Autowired
  @Getter
  private AdminRepository adminRepository;

  public Admin login(String username, String password) {
    Admin admin = adminRepository.findByUsername(username);
    if (null == admin || !admin.checkPassword(password)) {
      throw new ResponseViewException("admin/login", ImmutableMap.of("message", "用户名或密码错误"));
    }
    return admin;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Admin dbAdmin = adminRepository.findByUsername("admin");
    if (null == dbAdmin) {
      Admin admin = new Admin();
      admin.setUsername("admin");
      admin.setPassword("123");
      adminRepository.save(admin);
    }
  }
}
