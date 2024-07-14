package com.github.malahor.equeue.server;

import com.github.malahor.equeue.domain.Customer;
import com.github.malahor.equeue.domain.QueuePosition;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final QueueService queueService;
  private final CustomerRepository repository;
  private final MessageSender sender;

  public void register(UUID id) {
    queueService
        .queueContains(id)
        .ifPresentOrElse(i -> handleAlreadyRegistered(id, i), () -> handleRegistration(id));
  }

  private void handleAlreadyRegistered(UUID id, Integer i) {
    var record = repository.findById(id).orElseThrow();
    var queuePosition = new QueuePosition(record.getId(), record.getNumber(), i);
    sender.confirm(queuePosition);
  }

  private void handleRegistration(UUID id) {
    var queuePosition = queueService.putIntoQueue(id);
    var customer = Customer.registered(queuePosition);
    repository.save(customer);
    sender.confirm(queuePosition);
  }
}
