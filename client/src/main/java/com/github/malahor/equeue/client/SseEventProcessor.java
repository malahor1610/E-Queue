package com.github.malahor.equeue.client;

import com.github.malahor.equeue.domain.QueuePosition;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class SseEventProcessor {
  private final Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

  public SseEmitter createSseEmitter(String id) {
    return sseEmitters.putIfAbsent(id, new SseEmitter());
  }

  @SneakyThrows
  @Async
  public synchronized void sendConfirmation(QueuePosition queuePosition) {
    var sseEmitter = sseEmitters.get(queuePosition.getId());
    if (sseEmitter != null) {
      try {
        sseEmitter.send(queuePosition.toString());
      } catch (Exception e) {
        completeSseEmitter(queuePosition);
      }
    }
  }

  @Async
  public synchronized void completeSseEmitter(QueuePosition queuePosition) {
    var sseEmitter = sseEmitters.remove(queuePosition.getId());
    if (sseEmitter != null) sseEmitter.complete();
  }
}
