package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@EnableAuthorizationServer
@SpringBootApplication
public class CustomerServiceProducerApplication {

  public static void main(String[] args) {
    SpringApplication.run(CustomerServiceProducerApplication.class, args);
  }

}
