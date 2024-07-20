package com.github.malahor.equeue.domain;

import jakarta.persistence.*;
import lombok.*;

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
  private String comment;

  @Enumerated(EnumType.STRING)
  private CustomerTopic topic;

  @Embedded private Result result;

  public static Customer registered(Form form, QueuePosition queuePosition) {
    return Customer.builder()
        .id(queuePosition.getId())
        .number(queuePosition.getNumber())
        .firstName(form.getFirstName())
        .lastName(form.getLastName())
        .comment(form.getComment())
        .topic(form.getTopic())
        .build();
  }

  public void updateWithResult(Result result) {
    this.result = result;
  }
}
