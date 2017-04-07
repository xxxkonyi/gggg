package com.believe.core.web;

import com.believe.core.config.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/7 15:18
 * @since 1.0
 */
@Component
@Slf4j
public class GlobalReferenceDataBuilder implements ServletContextAware, InitializingBean {

  @Autowired
  private ApplicationProperties applicationProperties;

  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("GlobalReferenceDataBuilder init");
  }

  @Override
  public void setServletContext(ServletContext servletContext) {
    servletContext.setAttribute("resourceBase", applicationProperties.getResourceBase());
  }
}
