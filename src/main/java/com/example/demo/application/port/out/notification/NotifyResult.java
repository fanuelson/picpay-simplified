package com.example.demo.application.port.out.notification;

public sealed interface NotifyResult {
  record Ok() implements NotifyResult {}
  record Failed(String reason) implements NotifyResult {}
}
