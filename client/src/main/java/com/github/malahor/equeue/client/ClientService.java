package com.github.malahor.equeue.client;

import com.github.malahor.equeue.domain.Form;
import com.github.malahor.equeue.domain.QueuePosition;
import com.github.malahor.equeue.domain.Result;
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

  public void sendForm(String id, Form form) {
    form.setId(id);
    sender.sendForm(form);
  }

  public void confirm(QueuePosition queuePosition) {
    var sseEmitter = eventProcessor.getSseEmitter(queuePosition.getId());
    if (sseEmitter == null) throw new RuntimeException();
    eventProcessor.sendConfirmation(sseEmitter, queuePosition);
  }

    public void result(Result result) {
      var sseEmitter = eventProcessor.getSseEmitter(result.getId());
      if (sseEmitter == null) throw new RuntimeException();
      eventProcessor.sendResult(sseEmitter, result);
    }
}
