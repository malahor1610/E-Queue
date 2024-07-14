package com.github.malahor.equeue.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
public class ClientController {

  private final ClientService service;

  @GetMapping
  public RedirectView home() {
    return new RedirectView("/start");
  }

  @GetMapping("/start")
  public ModelAndView start(ModelMap model, @ModelAttribute("success") String success) {
    model.addAttribute("success", success);
    return new ModelAndView("start");
  }

  @PostMapping("/register")
  public RedirectView registerInQueue(RedirectAttributes attributes) {
    service.register();
    attributes.addFlashAttribute("success", "Confirmed stand in the queue");
    return new RedirectView("/start");
  }
}
