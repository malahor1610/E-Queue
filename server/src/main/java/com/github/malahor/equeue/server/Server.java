package com.github.malahor.equeue.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.github.malahor.equeue.server")
@EntityScan("com.github.malahor.equeue.domain")
public class Server {
  public static void main(String[] args) {
    SpringApplication.run(Server.class, args);
  }
}
