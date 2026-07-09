package com.example.demo.application.port.out;

import com.example.demo.domain.CustomerId;
import com.example.demo.domain.money.Money;

public record AuthorizeCommand(
    CustomerId payerId,
    CustomerId payeeId,
    Money amount
) {
}
