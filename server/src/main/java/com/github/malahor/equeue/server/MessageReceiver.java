package com.github.malahor.equeue.server;

import com.github.malahor.equeue.config.KafkaTopicConfig;
import com.github.malahor.equeue.domain.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageReceiver {

  private final CustomerService service;

  @KafkaListener(
      id = KafkaTopicConfig.REGISTER_TOPIC_NAME,
      topics = KafkaTopicConfig.REGISTER_TOPIC_NAME,
      containerFactory = "registerListenerContainerFactory")
  public void register(String id) {
    service.register(id);
  }

  @KafkaListener(
      id = KafkaTopicConfig.FORM_TOPIC_NAME,
      topics = KafkaTopicConfig.FORM_TOPIC_NAME,
      containerFactory = "formListenerContainerFactory")
  public void receiveForm(Customer customer) {
    service.receiveForm(customer);
  }
}
