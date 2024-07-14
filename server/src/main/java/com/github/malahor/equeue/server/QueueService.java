package com.github.malahor.equeue.server;

import com.github.malahor.equeue.config.KafkaTopicConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueService {

  @KafkaListener(
      id = KafkaTopicConfig.REGISTER_TOPIC_NAME,
      topics = KafkaTopicConfig.REGISTER_TOPIC_NAME,
      containerFactory = "registerListenerContainerFactory")
  public void register() {
    System.out.println("Received Message");
  }
}
