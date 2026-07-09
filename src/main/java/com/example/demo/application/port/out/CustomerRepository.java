package com.example.demo.application.port.out;

import com.example.demo.domain.Customer;
import com.example.demo.domain.CustomerId;

import java.util.Optional;

public interface CustomerRepository {
  Optional<Customer> findById(CustomerId id);
}
