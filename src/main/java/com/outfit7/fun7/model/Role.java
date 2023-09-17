package com.outfit7.fun7.model;

import lombok.Getter;

@Getter
public enum Role {
  USER("USER"),
  ADMIN("ADMIN");

  private final String key;

  Role(String key) {
    this.key = key;
  }
}
