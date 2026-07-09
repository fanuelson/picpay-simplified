package com.example.demo.adapter.out.persistence.wallet;

import com.example.demo.domain.CustomerId;
import com.example.demo.domain.Wallet;
import com.example.demo.domain.money.Money;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "wallets")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class WalletEntity {

  @Id
  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "balance_in_cents")
  private Long balanceInCents;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  public static WalletEntity from(Wallet wallet) {
    return new WalletEntity(
        wallet.getCustomerId().value(),
        wallet.getBalance().getAmountInCents(),
        wallet.getCreatedAt(),
        wallet.getUpdatedAt()
    );
  }

  public Wallet toDomain() {
    return new Wallet(new CustomerId(customerId), Money.of(balanceInCents), createdAt, updatedAt);
  }
}
