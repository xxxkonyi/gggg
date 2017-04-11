package com.believe.core.config;

import com.believe.core.service.impl.AdminServiceImpl;
import com.believe.core.service.impl.WechatSupport;
import com.believe.core.web.AdminSecurityInterceptor;
import com.believe.core.web.AuthenticationInterceptor;
import com.believe.core.web.WxSdkConfigInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.Executor;

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

  @Autowired
  private WechatSupport wechatSupport;

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

/*    WxSdkConfigInterceptor wxSdkConfigInterceptor = new WxSdkConfigInterceptor();
    wxSdkConfigInterceptor.setWechatSupport(wechatSupport);
    registry.addInterceptor(wxSdkConfigInterceptor)
      .excludePathPatterns("/wx*//**", "/admin*//**", "/error", "/general");*/
  }

  @Bean(name = "taskExecutor")
  public Executor asyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(2);
    executor.setMaxPoolSize(4);
    executor.setQueueCapacity(10);
    executor.setThreadNamePrefix("task-executor-");
    return executor;
  }

}
