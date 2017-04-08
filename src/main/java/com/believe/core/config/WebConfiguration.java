package com.believe.core.config;

import com.believe.core.repository.AdminRepository;
import com.believe.core.service.impl.AdminServiceImpl;
import com.believe.core.web.AdminSecurityInterceptor;
import com.believe.core.web.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 13:36
 * @since 1.0
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

  @Autowired
  private AdminServiceImpl adminService;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    AuthenticationInterceptor authenticationInterceptor = new AuthenticationInterceptor();
    registry.addInterceptor(authenticationInterceptor)
      .addPathPatterns("/auth/**");

    AdminSecurityInterceptor adminSecurityInterceptor = new AdminSecurityInterceptor();
    adminSecurityInterceptor.setAdminService(adminService);
    registry.addInterceptor(adminSecurityInterceptor)
      .addPathPatterns("/admin/**");
//      .excludePathPatterns("/admin/login");
  }

}
