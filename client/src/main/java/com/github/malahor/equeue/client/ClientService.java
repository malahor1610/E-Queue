package com.github.malahor.equeue.client;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final KafkaTemplate<Integer, String> kafkaTemplate;
    @Value(value = "${message.topic.name}")
    private String topicName;

    @SneakyThrows
    public String sendMessage(Customer customer){
        var result = kafkaTemplate.send(topicName, customer.toString());
        return result.get().toString();
    }
}
