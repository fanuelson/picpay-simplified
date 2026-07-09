package com.example.demo.adapter.out.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

@Slf4j
@Configuration
public class RestClientConfig {

  @Value("${external.authorization.url}")
  private String authorizationUrl;

  @Bean
  public RestClient authorizationRestClient() {
    return RestClient.builder()
      .baseUrl(authorizationUrl)
      .requestInterceptor(loggingInterceptor())
      .build();
  }

  private ClientHttpRequestInterceptor loggingInterceptor() {
    return (request, body, execution) -> {
      long start = System.currentTimeMillis();
      log.info("→ Request: {} {}", request.getMethod(), request.getURI());
      var response = execution.execute(request, body);
      long duration = System.currentTimeMillis() - start;
      log.info("← Response: {} ({}ms)", response.getStatusCode(), duration);
      return response;
    };
  }
}