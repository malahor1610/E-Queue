package com.github.malahor.equeue.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

  public static final String REGISTER_TOPIC_NAME = "register";
  public static final String CONFIRM_TOPIC_NAME = "confirm";
  public static final String APPROVE_TOPIC_NAME = "approve";

  @Bean
  public NewTopic register() {
    return TopicBuilder.name(REGISTER_TOPIC_NAME).build();
  }

  @Bean
  public NewTopic confirm() {
    return TopicBuilder.name(CONFIRM_TOPIC_NAME).build();
  }

  @Bean
  public NewTopic approve() {
    return TopicBuilder.name(APPROVE_TOPIC_NAME).build();
  }
}
