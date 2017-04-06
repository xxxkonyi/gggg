package com.believe;


import com.believe.core.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class MarketingApplication {

  public static void main(String[] args) {
    SpringApplication.run(MarketingApplication.class);
  }

}
