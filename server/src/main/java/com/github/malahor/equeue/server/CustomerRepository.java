package com.github.malahor.equeue.server;

import com.github.malahor.equeue.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {}
