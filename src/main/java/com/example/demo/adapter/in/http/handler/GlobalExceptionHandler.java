package com.example.demo.adapter.in.http.handler;

import com.example.demo.application.exception.AuthorizationFailedException;
import com.example.demo.application.exception.NotificationFailedException;
import com.example.demo.domain.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception ex) {
    final var status = HttpStatus.INTERNAL_SERVER_ERROR;
    return ResponseEntity.status(status.value()).body(ex.getMessage());
  }

  @ExceptionHandler(AuthorizationFailedException.class)
  public ResponseEntity<?> handleAuthorizationFailedException(AuthorizationFailedException ex) {
    final var msg = "authorization service unavailable: " + ex.getMessage();
    logger.warn(msg);
    final var status = HttpStatus.SERVICE_UNAVAILABLE;
    return ResponseEntity.status(status.value()).body(msg);
  }

  @ExceptionHandler(NotificationFailedException.class)
  public ResponseEntity<?> handleNotificationFailedException(NotificationFailedException ex) {
    final var msg = "notification service unavailable: " + ex.getMessage();
    logger.warn(msg);
    final var status = HttpStatus.SERVICE_UNAVAILABLE;
    return ResponseEntity.status(status.value()).body(msg);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<?> handleBusinessException(BusinessException ex) {
    final var msg = "business exception: " + ex.getMessage();
    logger.warn(msg);
    final var status = HttpStatus.BAD_REQUEST;
    return ResponseEntity.status(status.value()).body(msg);
  }

}