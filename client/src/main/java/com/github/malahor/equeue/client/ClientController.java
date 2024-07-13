package com.github.malahor.equeue.client;

import com.github.malahor.equeue.domain.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientService service;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody Customer customer) {
        return ResponseEntity.ok(service.sendMessage(customer));
    }
}
