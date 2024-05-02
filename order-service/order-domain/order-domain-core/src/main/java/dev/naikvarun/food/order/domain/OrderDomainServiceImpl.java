package dev.naikvarun.food.order.domain;

import dev.naikvarun.food.order.domain.entity.Order;
import dev.naikvarun.food.order.domain.entity.Product;
import dev.naikvarun.food.order.domain.entity.Restaurant;
import dev.naikvarun.food.order.domain.event.OrderCancelledEvent;
import dev.naikvarun.food.order.domain.event.OrderCreatedEvent;
import dev.naikvarun.food.order.domain.event.OrderPaidEvent;
import dev.naikvarun.food.order.domain.exceptions.OrderDomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class OrderDomainServiceImpl implements OrderDomainService {

    private final Logger logger = LoggerFactory.getLogger(OrderDomainServiceImpl.class);
    private final String UTC = "UTC";
    private final ZoneId ZONE_UTC = ZoneId.of(UTC);

    @Override
    public OrderCreatedEvent validateAndInitializeOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);
        order.validate();
        order.initialize();
        logger.info("Order with id {} is initialized", order.getId());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZONE_UTC));
    }

    // TODO: Optimize it
    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        order.getItems().forEach(item -> {
            restaurant.getProducts().forEach(requestedProduct -> {
                Product currentProduct = item.getProduct();
                if (currentProduct.equals(requestedProduct)) {
                    currentProduct.updateWithConfirmedNameAndPrice(requestedProduct.getName(), requestedProduct.getPrice());
                }
            });
        });
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive()) {
            throw new OrderDomainException("Restaurant " + restaurant.getId() + " is not currently active ");
        }
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        logger.info("Order with id {} paid successfully", order.getId());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZONE_UTC));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        logger.info("Order with id {} approved successfully", order.getId());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessage) {
        order.initCancellation(failureMessage);
        logger.info("Order with id {} payment cancelling", order.getId());
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZONE_UTC));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessage) {
        order.cancel(failureMessage);
        logger.info("Order with id {} cancelled successfully", order.getId());
    }
}
