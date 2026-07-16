package com.example.demo.application.exception;

public class NotificationFailedException extends RuntimeException {
  public NotificationFailedException(String message) {
    super(message);
  }
}
