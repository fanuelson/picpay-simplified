package com.example.demo.adapter.out.persistence.customer;


import com.example.demo.domain.Customer;
import com.example.demo.domain.CustomerId;
import com.example.demo.domain.CustomerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customers")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "full_name", nullable = false, length = 255)
  private String fullName;

  @Column(name = "document", nullable = false, unique = true, length = 14)
  private String document;

  @Column(name = "email", nullable = false, unique = true, length = 255)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "customer_type", nullable = false, length = 20)
  private CustomerType type;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  public Customer toDomain() {
    return Customer.builder()
        .id(new CustomerId(id))
        .fullName(fullName)
        .document(document)
        .email(email)
        .type(type)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();
  }
}
