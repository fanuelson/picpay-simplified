package com.example.demo.domain;

import static java.util.Objects.requireNonNull;

public record CustomerId(Long value) {

  public CustomerId {
    requireNonNull(value, "empty customer id");
  }
}
