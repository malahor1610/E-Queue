package com.github.malahor.equeue.server;

import com.github.malahor.equeue.domain.Result;
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
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
public class ServerController {

  private final CustomerService service;
  private final SseEventProcessor sseEventProcessor;

  @GetMapping("/emitter")
  public SseEmitter createSseEmitter(@CookieValue(name = "user-id") String id) {
    return sseEventProcessor.createSseEmitter(id);
  }

  @GetMapping
  public RedirectView home() {
    return new RedirectView("/queue");
  }

  @GetMapping("/queue")
  public ModelAndView queueView(HttpServletRequest request, HttpServletResponse response) {
    if (userIdCookieMissing(request)) attachUserIdCookie(response);
    return new ModelAndView("queue");
  }

  @GetMapping("/form")
  public ModelAndView formView(ModelMap model) {
    var customer = service.serveCustomer();
    model.addAttribute("form", customer.getInfo());
    model.addAttribute("result", new Result(customer.getId()));
    return new ModelAndView("form");
  }

  @PostMapping(value = "/form", params = "action=approve")
  public RedirectView approve(@ModelAttribute("result") Result result) {
    result.setApproval(true);
    service.saveResult(result);
    return new RedirectView("/queue");
  }

  @PostMapping(value = "/form", params = "action=reject")
  public RedirectView reject(@ModelAttribute("result") Result result) {
    result.setApproval(false);
    service.saveResult(result);
    return new RedirectView("/queue");
  }

  private boolean userIdCookieMissing(HttpServletRequest request) {
    return request.getCookies() == null
        || Arrays.stream(request.getCookies())
            .noneMatch(cookie -> cookie.getName().equals("user-id"));
  }

  private void attachUserIdCookie(HttpServletResponse response) {
    var id = UUID.randomUUID().toString();
    var cookie = new Cookie("user-id", id);
    cookie.setHttpOnly(true);
    cookie.setDomain("localhost");
    response.addCookie(cookie);
  }
}
