package com.github.malahor.equeue.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Customer {

  @Id private String id;
  private Integer number;
  private String firstName;
  private String lastName;
  private CustomerTopic topic;
  private String comment;

  public static Customer registered(QueuePosition queuePosition) {
    return Customer.builder().id(queuePosition.getId()).number(queuePosition.getNumber()).build();
  }

  public void updateWithForm(Customer customer) {
    this.firstName = customer.getFirstName();
    this.lastName = customer.getLastName();
    this.topic = customer.getTopic();
    this.comment = customer.getComment();
  }
}
