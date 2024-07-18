package com.github.malahor.equeue.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Embeddable
public class Form {

  @Transient private String id;
  private String firstName;
  private String lastName;
  private String comment;

  @Enumerated(EnumType.STRING)
  private CustomerTopic topic;
}
