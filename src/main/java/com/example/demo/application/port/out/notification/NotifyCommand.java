package com.example.demo.application.port.out.notification;

public record NotifyCommand(
    String destination,
    String msg
) {
}
