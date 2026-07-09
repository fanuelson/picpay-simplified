package com.example.demo.adapter.out.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Slf4j
@Configuration
public class RestClientConfig {

  @Value("${external.authorization.url}")
  private String authorizationUrl;

  @Value("${external.authorization.connect-timeout:2s}")
  private Duration connectTimeout;

  @Value("${external.authorization.read-timeout:3s}")
  private Duration readTimeout;

  @Bean
  public RestClient authorizationRestClient() {
    return RestClient.builder()
      .baseUrl(authorizationUrl)
      .requestFactory(clientHttpRequestFactory())
      .requestInterceptor(loggingInterceptor())
      .build();
  }

  private SimpleClientHttpRequestFactory clientHttpRequestFactory() {
    final var factory = new SimpleClientHttpRequestFactory();
    factory.setConnectTimeout(connectTimeout);
    factory.setReadTimeout(readTimeout);
    return factory;
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