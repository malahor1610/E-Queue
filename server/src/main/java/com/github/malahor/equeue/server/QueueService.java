package com.github.malahor.equeue.server;

import com.github.malahor.equeue.domain.QueuePosition;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueService {

  private final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
  private Integer number = 0;

  public Optional<Integer> queueContains(String id) {
    if (!queue.contains(id)) return Optional.empty();
    var i = queue.stream().toList().indexOf(id);
    return Optional.of(i);
  }

  public synchronized QueuePosition putIntoQueue(String id) {
    number++;
    queue.offer(id);
    return QueuePosition.builder().id(id).number(number).pending(queue.size() - 1).build();
  }
}
