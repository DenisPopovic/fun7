package com.outfit7.fun7.model;

import lombok.Getter;

@Getter
public enum ActivisionStatus {
  ENABLED("enabled"),
  DISABLED("disabled");

  private final String state;

  ActivisionStatus(String state) {
    this.state = state;
  }
}
