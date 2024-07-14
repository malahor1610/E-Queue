package com.github.malahor.equeue.server;

import com.github.malahor.equeue.config.KafkaTopicConfig;
import com.github.malahor.equeue.domain.QueuePosition;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSender {

  private final KafkaTemplate<String, QueuePosition> confirmKafkaTemplate;

  @SneakyThrows
  public void confirm(QueuePosition queuePosition) {
    confirmKafkaTemplate.send(KafkaTopicConfig.CONFIRM_TOPIC_NAME, queuePosition);
  }
}
