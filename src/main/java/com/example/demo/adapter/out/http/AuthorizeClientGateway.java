package com.example.demo.adapter.out.http;

import com.example.demo.application.port.out.AuthorizationGateway;
import com.example.demo.application.port.out.AuthorizeCommand;
import com.example.demo.application.port.out.AuthorizeResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

import static java.util.Objects.isNull;

@Slf4j
@Service
public class AuthorizeClientGateway implements AuthorizationGateway {

  @Autowired
  @Qualifier("authorizationRestClient")
  private RestClient restClient;

  @Override
  public AuthorizeResult authorize(AuthorizeCommand command) {
    final AuthorizeResponse response;
    try {
      response = restClient.get()
          .uri("/authorize")
          .retrieve()
          .body(AuthorizeResponse.class);
    } catch (RestClientException e) {
      log.warn("authorization call failed: {}", e.getMessage());
      return new AuthorizeResult.Failed(e.getMessage());
    }

    if (isNull(response) || response.isUnauthorized()) {
      return new AuthorizeResult.Unauthorized("Unauthorized");
    }

    return new AuthorizeResult.Authorized(generateAuthorizationCode());
  }

  private String generateAuthorizationCode() {
    return "AUTH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
  }

}
