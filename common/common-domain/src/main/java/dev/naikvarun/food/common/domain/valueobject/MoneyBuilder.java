package dev.naikvarun.food.common.domain.valueobject;

import org.javamoney.moneta.Money;

public class MoneyBuilder {
    static Money from(Number amount) {
        return Money.of(amount, "USD");
    }
}
