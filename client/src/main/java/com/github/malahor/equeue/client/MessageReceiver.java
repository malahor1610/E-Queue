package com.github.malahor.equeue.client;

import com.github.malahor.equeue.config.KafkaTopicConfig;
import com.github.malahor.equeue.domain.QueuePosition;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageReceiver {

  @KafkaListener(
      id = KafkaTopicConfig.CONFIRM_TOPIC_NAME,
      topics = KafkaTopicConfig.CONFIRM_TOPIC_NAME,
      containerFactory = "confirmListenerContainerFactory")
  public void register(QueuePosition queuePosition) {
    System.out.printf(
        "Your number is %d and there are %d people before You",
        queuePosition.getNumber(), queuePosition.getPending());
  }
}
