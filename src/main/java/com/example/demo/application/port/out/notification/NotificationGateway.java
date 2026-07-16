package com.example.demo.application.port.out.notification;


public interface NotificationGateway {
  NotifyResult notify(NotifyCommand command);
}