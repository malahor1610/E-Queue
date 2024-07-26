package com.github.malahor.equeue.domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class Form {

  private String id;
  private String firstName;
  private String lastName;
  private String comment;

  @Enumerated(EnumType.STRING)
  private CustomerTopic topic;
}
