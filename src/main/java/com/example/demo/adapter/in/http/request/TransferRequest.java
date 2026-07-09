package com.example.demo.adapter.in.http.request;

public record TransferRequest(
  Long payer,
  Long payee,
  String amount
) {
}
