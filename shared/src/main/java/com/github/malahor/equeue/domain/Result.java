package com.github.malahor.equeue.domain;

import java.util.UUID;
import lombok.Data;

@Data
public class Result {

  private UUID id;
  private Boolean approval;
  private String comment;
}
