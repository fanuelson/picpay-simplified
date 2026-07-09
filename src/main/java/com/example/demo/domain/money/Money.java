package com.example.demo.domain.money;

import java.math.BigDecimal;

public interface Money {

  Money ZERO = MoneyInCents.of(BigDecimal.ZERO);

  static Money of(long moneyInCents) {
    return MoneyInCents.of(moneyInCents);
  }

  static Money of(String money) {
    return MoneyInCents.of(money);
  }

  Money add(Money money);
  Money subtract(Money money);
  long getAmountInCents();
  boolean isZero();
  boolean isLessThanOrEqual(Money other);
}
