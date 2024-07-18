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

  public SseEmitter getSseEmitter(String id) {
    return sseEmitters.get(id);
  }

  @SneakyThrows
  @Async
  public synchronized void sendConfirmation(SseEmitter sseEmitter, QueuePosition queuePosition) {
    try {
      sseEmitter.send(queuePosition.toString());
    } catch (Exception e) {
      completeSseEmitter(queuePosition.getId());
    }
  }

  @Async
  public synchronized void completeSseEmitter(String id) {
    var sseEmitter = sseEmitters.remove(id);
    if (sseEmitter != null) sseEmitter.complete();
  }
}
