package com.github.malahor.equeue.domain;

import lombok.Getter;

@Getter
public enum CustomerTopic {
  THE_THING("The thing"),
  THE_OTHER_ONE("The other one"),
  YOU_KNOW_WHAT("You know");

  private final String name;

  CustomerTopic(String name) {
    this.name = name;
  }
}
