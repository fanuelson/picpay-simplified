package com.example.demo.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Customer {
  private CustomerId id;
  private String document;
  private String fullName;
  private String email;
  private String password;
  private CustomerType type;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
