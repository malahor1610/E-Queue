package com.github.malahor.equeue.client;

import com.github.malahor.equeue.config.KafkaTopicConfig;
import com.github.malahor.equeue.domain.Form;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSender {

  private final KafkaTemplate<String, Form> registerKafkaTemplate;

  @SneakyThrows
  public void sendForm(Form form) {
    registerKafkaTemplate.send(KafkaTopicConfig.REGISTER_TOPIC_NAME, form);
  }
}
