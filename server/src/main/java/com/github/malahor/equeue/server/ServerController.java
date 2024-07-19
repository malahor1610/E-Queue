package com.github.malahor.equeue.server;

import com.github.malahor.equeue.domain.CustomerTopic;
import com.github.malahor.equeue.domain.Form;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

//  @PostMapping("/queue")
//  public ModelAndView register(ModelMap model, @CookieValue(name = "user-id") String id) {
//    Thread.startVirtualThread(() -> service.register(id));
//    model.addAttribute("initializeEventEmitter", true);
//    return new ModelAndView("register");
//  }

//  @GetMapping("/form")
//  public ModelAndView formView(ModelMap model) {
//    model.addAttribute("topics", CustomerTopic.values());
//    model.addAttribute("form", new Form());
//    return new ModelAndView("form");
//  }
//
//  @PostMapping("/form")
//  public RedirectView form(
//      @CookieValue(name = "user-id") String id, @ModelAttribute("form") Form form) {
//    Thread.startVirtualThread(() -> service.sendForm(id, form));
//    return new RedirectView("/result");
//  }
//
//  @GetMapping("/result")
//  public ModelAndView resultView() {
//    return new ModelAndView("result");
//  }

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
