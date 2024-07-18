package com.github.malahor.equeue.server;

import com.github.malahor.equeue.domain.Customer;
import com.github.malahor.equeue.domain.QueuePosition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final QueueService queueService;
  private final CustomerRepository repository;
  private final MessageSender sender;

  public void register(String id) {
    queueService
        .queueContains(id)
        .ifPresentOrElse(i -> handleAlreadyRegistered(id, i), () -> handleRegistration(id));
  }

  private void handleAlreadyRegistered(String id, Integer i) {
    var record = repository.findById(id).orElseThrow();
    var queuePosition =
        QueuePosition.builder().id(record.getId()).number(record.getNumber()).pending(i).build();
    sender.confirm(queuePosition);
  }

  private void handleRegistration(String id) {
    var queuePosition = queueService.putIntoQueue(id);
    var customer = Customer.registered(queuePosition);
    repository.save(customer);
    sender.confirm(queuePosition);
  }
}
