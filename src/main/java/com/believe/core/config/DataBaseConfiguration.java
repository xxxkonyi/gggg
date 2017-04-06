package com.believe.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 13:36
 * @since 1.0
 */
@Configuration
@EnableMongoRepositories("com.believe.core.repository")
public class DataBaseConfiguration {

  @Bean
  public DateCreatorMongoEventListener dateCreatorMongoEventListener() {
    return new DateCreatorMongoEventListener();
  }

  @Bean
  public ValidatingMongoEventListener validatingMongoEventListener() {
    return new ValidatingMongoEventListener(validator());
  }

  @Bean
  public LocalValidatorFactoryBean validator() {
    return new LocalValidatorFactoryBean();
  }

}
