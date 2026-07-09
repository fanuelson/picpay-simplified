package com.example.demo.adapter.out.persistence.customer;

import com.example.demo.application.port.out.CustomerRepository;
import com.example.demo.domain.Customer;
import com.example.demo.domain.CustomerId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

  private final CustomerJpaRepository repository;

  @Override
  public Optional<Customer> findById(CustomerId customerId) {
    return repository.findById(customerId.value())
        .map(CustomerEntity::toDomain);
  }

}
