package com.believe.core.config;

import com.believe.core.web.AuthenticationInterceptor;
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

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    AuthenticationInterceptor authenticationInterceptor = new AuthenticationInterceptor();
    registry.addInterceptor(authenticationInterceptor)
      .addPathPatterns("/auth/**");
  }

}
