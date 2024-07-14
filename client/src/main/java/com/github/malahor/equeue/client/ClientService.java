package com.github.malahor.equeue.client;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

  private final HttpServletRequest request;
  private final MessageSender sender;

  @SneakyThrows
  public void register() {
    var id = UUID.randomUUID();
    request.getSession().setAttribute("clientId", id);
    sender.register(id);
  }
}
