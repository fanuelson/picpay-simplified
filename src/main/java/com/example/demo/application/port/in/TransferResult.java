package com.example.demo.application.port.in;

public sealed interface TransferResult {
  record Ok() implements TransferResult {}
  record Failed(String reason) implements TransferResult {}
}
