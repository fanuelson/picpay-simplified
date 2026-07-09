package com.example.demo.adapter.out.persistence.wallet;

import com.example.demo.application.port.out.WalletRepository;
import com.example.demo.domain.CustomerId;
import com.example.demo.domain.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WalletRepositoryImpl implements WalletRepository {

  private final WalletJpaRepository repository;

  @Override
  public Optional<Wallet> findByCustomerId(CustomerId customerId) {
    return repository.findByCustomerId(customerId.value())
        .map(WalletEntity::toDomain);
  }

  @Override
  public Wallet save(Wallet wallet) {
    return repository.save(WalletEntity.from(wallet)).toDomain();
  }
}
