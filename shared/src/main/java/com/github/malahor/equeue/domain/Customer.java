package com.github.malahor.equeue.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Customer {

  @Id private String id;
  private Integer number;
  private String firstName;
  private String lastName;

  public static Customer registered(QueuePosition queuePosition) {
    return Customer.builder().id(queuePosition.getId()).number(queuePosition.getNumber()).build();
  }
}
