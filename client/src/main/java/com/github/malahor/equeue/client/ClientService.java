package com.github.malahor.equeue.client;

import com.github.malahor.equeue.domain.QueuePosition;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

  private final HttpServletRequest request;
  private final MessageSender sender;
  private final SseEventProcessor eventProcessor;

  @SneakyThrows
  public void register(String id) {
    request.getSession().setAttribute("clientId", id);
    sender.register(id);
  }

  public void confirm(QueuePosition queuePosition) {
    eventProcessor.sendConfirmation(queuePosition);
  }
}
