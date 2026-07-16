package com.example.demo.application.port.out.authorization;


public interface AuthorizationGateway {
  AuthorizeResult authorize(AuthorizeCommand command);
}