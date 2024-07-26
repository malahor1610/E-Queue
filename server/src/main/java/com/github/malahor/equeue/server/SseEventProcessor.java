package com.github.malahor.equeue.server;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class SseEventProcessor {
  private final Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

  public SseEmitter createSseEmitter(String id) {
    return sseEmitters.put(id, new SseEmitter());
  }

  public Set<Map.Entry<String, SseEmitter>> getSseEmitters() {
    return sseEmitters.entrySet();
  }

  @SneakyThrows
  @Async
  public synchronized void sendQueueStatus(
      Map.Entry<String, SseEmitter> emitterEntry, String message) {
    try {
      emitterEntry.getValue().send(message);
    } catch (Exception e) {
      completeSseEmitter(emitterEntry.getKey());
    }
  }

  @Async
  public synchronized void completeSseEmitter(String id) {
    var sseEmitter = sseEmitters.remove(id);
    if (sseEmitter != null) sseEmitter.complete();
  }
}
