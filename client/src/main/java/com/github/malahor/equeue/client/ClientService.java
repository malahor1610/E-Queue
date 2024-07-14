package com.github.malahor.equeue.client;

import com.github.malahor.equeue.config.KafkaTopicConfig;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

  private final KafkaTemplate<UUID, Void> registerKafkaTemplate;

  @SneakyThrows
  public void register() {
    registerKafkaTemplate.send(KafkaTopicConfig.REGISTER_TOPIC_NAME, null);
  }
}
