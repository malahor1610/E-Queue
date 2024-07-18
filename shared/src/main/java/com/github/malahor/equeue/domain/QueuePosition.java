package com.github.malahor.equeue.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueuePosition {

  private String id;
  private Integer number;
  private Integer pending;

  @Override
  public String toString() {
    return String.format("Your number is %d and there are %d people before You", number, pending);
  }
}
