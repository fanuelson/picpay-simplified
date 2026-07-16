package com.example.demo.adapter.out.http;

import com.example.demo.application.exception.NotificationFailedException;
import com.example.demo.application.port.out.notification.NotificationGateway;
import com.example.demo.application.port.out.notification.NotifyCommand;
import com.example.demo.application.port.out.notification.NotifyResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Slf4j
@Service
public class NotificationClientGateway implements NotificationGateway {

  @Autowired
  @Qualifier("notificationRestClient")
  private RestClient restClient;

  @Override
  public NotifyResult notify(NotifyCommand command) {
    final var response = restClient.post()
        .uri("/notify")
        .contentType(MediaType.APPLICATION_JSON)
        .body(Map.of("message", command))
        .retrieve()
        .onStatus(
            HttpStatusCode::isError,
            (req, res) -> {
              throw new NotificationFailedException(res.getStatusText());
            }
        )
        .toBodilessEntity();

    return new NotifyResult.Ok();
  }

}
