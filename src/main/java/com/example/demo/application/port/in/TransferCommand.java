package com.example.demo.application.port.in;

import com.example.demo.domain.CustomerId;
import com.example.demo.domain.money.Money;

public record TransferCommand(
    CustomerId payerId,
    CustomerId payeeId,
    Money amount
) {
}
