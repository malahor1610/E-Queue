package com.github.malahor.equeue.client;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
public class ClientController {

  private final ClientService service;
  private final SseEventProcessor sseEventProcessor;

  @GetMapping("/emitter")
  public SseEmitter createSseEmitter(@CookieValue(name = "user-id") String id) {
    return sseEventProcessor.createSseEmitter(id);
  }

  @GetMapping
  public RedirectView home() {
    return new RedirectView("/start");
  }

  @GetMapping("/start")
  public ModelAndView start(
      ModelMap model,
      @ModelAttribute("success") String success,
      HttpServletRequest request,
      HttpServletResponse response) {
    model.addAttribute("success", success);
    if (request.getCookies() == null
        || Arrays.stream(request.getCookies())
            .noneMatch(cookie -> cookie.getName().equals("user-id"))) {
      var id = UUID.randomUUID().toString();
      var cookie = new Cookie("user-id", id);
      cookie.setHttpOnly(true);
      cookie.setDomain("localhost");
      response.addCookie(cookie);
    }
    return new ModelAndView("start");
  }

  @PostMapping("/register")
  public RedirectView registerInQueue(
      RedirectAttributes attributes, @CookieValue(name = "user-id") String id) {
    service.register(id);
    attributes.addFlashAttribute("success", "Confirmed stand in the queue");
    return new RedirectView("/start");
  }
}
