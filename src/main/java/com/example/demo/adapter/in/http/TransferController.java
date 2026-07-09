package com.example.demo.adapter.in.http;

import com.example.demo.adapter.in.http.request.TransferRequest;
import com.example.demo.application.port.in.TransferCommand;
import com.example.demo.application.port.in.TransferResult;
import com.example.demo.application.port.in.TransferUseCase;
import com.example.demo.domain.CustomerId;
import com.example.demo.domain.money.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/transfer")
@RequiredArgsConstructor
public class TransferController {

  private final TransferUseCase transferUseCase;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public Object transfer(@RequestBody TransferRequest request) {
    final var command = new TransferCommand(
        new CustomerId(request.payer()),
        new CustomerId(request.payee()),
        Money.of(request.amount())
    );
    final var result = transferUseCase.execute(command);
    return switch (result) {
      case TransferResult.Ok ignored -> ResponseEntity.ok();
      case TransferResult.Failed reason -> ResponseEntity.badRequest().body(reason);
    };

  }
}
