package com.example.demo.application.port.out.authorization;

public sealed interface AuthorizeResult {
  record Authorized(String authorizationCode) implements AuthorizeResult {}
  record Unauthorized() implements AuthorizeResult {}
  record Failed(String reason) implements AuthorizeResult {}
}
