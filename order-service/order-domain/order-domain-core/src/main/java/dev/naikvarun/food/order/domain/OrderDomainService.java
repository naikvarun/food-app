package dev.naikvarun.food.order.domain;

import dev.naikvarun.food.order.domain.entity.Order;
import dev.naikvarun.food.order.domain.entity.Restaurant;
import dev.naikvarun.food.order.domain.event.OrderCancelledEvent;
import dev.naikvarun.food.order.domain.event.OrderCreatedEvent;
import dev.naikvarun.food.order.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitializeOrder(final Order order, final Restaurant restaurant);

    OrderPaidEvent payOrder(final Order order);

    void approveOrder(final Order order);

    OrderCancelledEvent cancelOrderPayment(final Order order, final List<String> failureMessage);

    void cancelOrder(final Order order, final List<String> failureMessage);
}
