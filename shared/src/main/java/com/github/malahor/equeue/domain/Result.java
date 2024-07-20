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

  @Override
  public String toString() {
    return String.format("By the decision of us, Your request was %s. Justification of the decision is as follows: %n%s.%nThank You for using our new app, You can now close the tab in a browser", approval ? "accepted" : "rejected", justification);
  }
}
