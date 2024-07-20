package com.github.malahor.equeue.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Customer {

  @Id private String id;
  private Integer number;
  @Embedded private Form info;
  @Embedded private Result result;

  public static Customer registered(QueuePosition queuePosition) {
    return Customer.builder().id(queuePosition.getId()).number(queuePosition.getNumber()).build();
  }

  public void updateWithForm(Form form) {
    info = form;
  }

  public void updateWithResult(Result result) {
    this.result = result;
  }
}
