package com.example.demo.application.port.out;


public interface AuthorizationGateway {
  AuthorizeResult authorize(AuthorizeCommand command);
}