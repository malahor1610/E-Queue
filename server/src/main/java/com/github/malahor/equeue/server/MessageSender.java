package com.github.malahor.equeue.server;

import com.github.malahor.equeue.config.KafkaTopicConfig;
import com.github.malahor.equeue.domain.QueuePosition;
import com.github.malahor.equeue.domain.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSender {

  private final KafkaTemplate<String, QueuePosition> confirmKafkaTemplate;
  private final KafkaTemplate<String, Result> approveKafkaTemplate;

  public void confirm(QueuePosition queuePosition) {
    confirmKafkaTemplate.send(KafkaTopicConfig.CONFIRM_TOPIC_NAME, queuePosition);
  }

  public void sendResult(Result result) {
    approveKafkaTemplate.send(KafkaTopicConfig.APPROVE_TOPIC_NAME, result);
  }
}
