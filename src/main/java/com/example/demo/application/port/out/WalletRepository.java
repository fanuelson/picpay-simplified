package com.example.demo.application.port.out;

import com.example.demo.domain.CustomerId;
import com.example.demo.domain.Wallet;

import java.util.Optional;

public interface WalletRepository {
  Optional<Wallet> findByCustomerId(CustomerId customerId);
  Wallet save(Wallet wallet);
}
