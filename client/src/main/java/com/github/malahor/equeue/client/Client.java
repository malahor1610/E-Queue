package com.github.malahor.equeue.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.github.malahor.equeue.client")
public class Client {
  public static void main(String[] args) {
    SpringApplication.run(Client.class, args);
  }
}
