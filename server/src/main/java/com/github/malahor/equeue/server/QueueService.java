package com.github.malahor.equeue.server;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueService {

    @KafkaListener(id="listener", topics = "topic1")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message: " + message);
    }
}
