package com.example.demo.domain;

import com.example.demo.domain.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@With
@Getter
@RequiredArgsConstructor
public class Wallet {

  private final CustomerId customerId;
  private final Money balance;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

}
