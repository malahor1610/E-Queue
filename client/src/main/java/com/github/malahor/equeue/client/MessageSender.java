package com.github.malahor.equeue.client;

import com.github.malahor.equeue.config.KafkaTopicConfig;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSender {

  private final KafkaTemplate<String, UUID> registerKafkaTemplate;

  @SneakyThrows
  public void register(UUID id) {
    registerKafkaTemplate.send(KafkaTopicConfig.REGISTER_TOPIC_NAME, id);
  }
}
