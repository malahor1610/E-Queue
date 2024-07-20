package com.github.malahor.equeue.server;

import com.github.malahor.equeue.domain.Customer;
import com.github.malahor.equeue.domain.Form;
import com.github.malahor.equeue.domain.QueuePosition;
import com.github.malahor.equeue.domain.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final QueueService queueService;
  private final CustomerRepository repository;
  private final MessageSender sender;
  private final SseEventProcessor eventProcessor;

  public void register(Form form) {
    var id = form.getId();
    queueService
        .queueContains(id)
        .ifPresentOrElse(i -> handleAlreadyRegistered(id, i), () -> handleRegistration(form));
  }

  private void handleAlreadyRegistered(String id, Integer i) {
    var record = repository.findById(id).orElseThrow();
    var queuePosition =
        QueuePosition.builder().id(record.getId()).number(record.getNumber()).pending(i).build();
    sender.confirm(queuePosition);
  }

  private void handleRegistration(Form form) {
    var queuePosition = queueService.putIntoQueue(form.getId());
    var customer = Customer.registered(form, queuePosition);
    repository.save(customer);
    sender.confirm(queuePosition);
    sendQueueStatus(queueService.queueSize());
  }

  public void sendQueueStatus(int queueSize) {
    var message =
        String.format(
            "There is a new record in the queue. Current size of the queue is %d", queueSize);
    var sseEmitters = eventProcessor.getSseEmitters();
    if (sseEmitters.isEmpty()) throw new RuntimeException();
    sseEmitters.forEach(
        emitterEntry -> {
          if (emitterEntry.getValue() == null) throw new RuntimeException();
          eventProcessor.sendQueueStatus(emitterEntry, message);
        });
  }

  public Customer serveCustomer() {
    var id = queueService.getFromQueue();
    if (id == null) throw new RuntimeException();
    return repository.findById(id).orElseGet(this::serveCustomer);
  }

  public void saveResult(Result result) {
    var customer = repository.findById(result.getId()).orElseThrow();
    customer.updateWithResult(result);
    sender.sendResult(result);
  }
}
