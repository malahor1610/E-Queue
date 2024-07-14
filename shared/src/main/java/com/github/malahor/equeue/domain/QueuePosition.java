package com.github.malahor.equeue.domain;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueuePosition {

  private UUID id;
  private Integer number;
  private Integer pending;
}
