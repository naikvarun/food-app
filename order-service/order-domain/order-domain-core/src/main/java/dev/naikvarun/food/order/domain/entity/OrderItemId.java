package dev.naikvarun.food.order.domain.entity;

import dev.naikvarun.food.common.domain.valueobject.BaseId;

public class OrderItemId extends BaseId<String> {
    public OrderItemId(String s) {
        super(s);
    }
}
