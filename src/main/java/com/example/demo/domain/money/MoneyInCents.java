package com.example.demo.domain.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyInCents implements Money {

  private final long amountInCents;

  private MoneyInCents(long moneyInCents) {
    this.amountInCents = moneyInCents;
  }

  public static Money of(long amountInCents) {
    return new MoneyInCents(amountInCents);
  }

  public static Money of(BigDecimal amount) {
    final var amountInCents = amount
        .movePointRight(2)
        .setScale(0, RoundingMode.HALF_EVEN)
        .longValue();
    return new MoneyInCents(amountInCents);
  }

  public static Money of(String amount) {
    return of(new BigDecimal(amount));
  }

  @Override
  public Money add(Money money) {
    return new MoneyInCents(this.amountInCents + money.getAmountInCents());
  }

  @Override
  public Money subtract(Money money) {
    return new MoneyInCents(this.amountInCents - money.getAmountInCents());
  }

  public long getAmountInCents() {
    return this.amountInCents;
  }

  @Override
  public boolean isZero() {
    return this.amountInCents == 0;
  }

  @Override
  public boolean isLessThanOrEqual(Money other) {
    return this.amountInCents <= other.getAmountInCents();
  }
}
