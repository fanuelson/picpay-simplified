package com.example.demo.application.port.out;

public sealed interface AuthorizeResult {
  record Authorized(String authorizationCode) implements AuthorizeResult {}
  record Unauthorized(String reason) implements AuthorizeResult {}
  record Failed(String reason) implements AuthorizeResult {}
}
