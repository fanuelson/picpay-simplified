package com.example.demo.adapter.out.persistence.wallet;


import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletJpaRepository extends JpaRepository<WalletEntity, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<WalletEntity> findByCustomerId(Long customerId);
}