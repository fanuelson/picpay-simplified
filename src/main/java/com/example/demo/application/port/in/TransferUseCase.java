package com.example.demo.application.port.in;

public interface TransferUseCase {
  TransferResult execute(TransferCommand command);
}
