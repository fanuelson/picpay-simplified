package com.example.demo.adapter.in.http.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransferRequest(

    @NotNull
    Long payer,

    @NotNull
    Long payee,

    @NotBlank
    String amount

) {
}
