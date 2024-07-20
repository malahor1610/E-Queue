package com.github.malahor.equeue.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class Result {

  @Transient private String id;
  private Boolean approval;
  private String justification;

  public Result(String id) {
    this.id = id;
  }
}
