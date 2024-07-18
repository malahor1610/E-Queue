package com.github.malahor.equeue.client;

import com.github.malahor.equeue.domain.Customer;
import com.github.malahor.equeue.domain.QueuePosition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

  private final MessageSender sender;
  private final SseEventProcessor eventProcessor;

  public void register(String id) {
    sender.register(id);
  }

  public void sendForm(String id, Customer customer) {
    customer.setId(id);
    sender.sendForm(customer);
  }

  public void confirm(QueuePosition queuePosition) {
    var sseEmitter = eventProcessor.getSseEmitter(queuePosition.getId());
    if (sseEmitter == null) throw new RuntimeException();
    eventProcessor.sendConfirmation(sseEmitter, queuePosition);
  }
}
