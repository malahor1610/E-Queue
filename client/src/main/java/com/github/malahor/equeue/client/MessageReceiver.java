package com.github.malahor.equeue.client;

import com.github.malahor.equeue.config.KafkaTopicConfig;
import com.github.malahor.equeue.domain.QueuePosition;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageReceiver {

  private final ClientService service;

  @KafkaListener(
      id = KafkaTopicConfig.CONFIRM_TOPIC_NAME,
      topics = KafkaTopicConfig.CONFIRM_TOPIC_NAME,
      containerFactory = "confirmListenerContainerFactory")
  public void register(QueuePosition queuePosition) {
    service.confirm(queuePosition);
  }
}
