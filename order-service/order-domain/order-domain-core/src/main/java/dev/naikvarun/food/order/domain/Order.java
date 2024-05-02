package dev.naikvarun.food.order.domain;

import dev.naikvarun.food.common.domain.entity.AggregateRoot;
import dev.naikvarun.food.common.domain.valueobject.CustomerId;
import dev.naikvarun.food.common.domain.valueobject.OrderId;
import dev.naikvarun.food.common.domain.valueobject.OrderStatus;
import dev.naikvarun.food.common.domain.valueobject.RestaurantId;
import dev.naikvarun.food.order.valueobject.StreetAddress;
import dev.naikvarun.food.order.valueobject.TrackingId;
import org.javamoney.moneta.Money;

import java.util.List;

public class Order extends AggregateRoot<OrderId> {
    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> items;
    private OrderStatus status;
    private TrackingId trackingId;
    private List<String>  failureMessages;

    private Order(Builder builder) {
        super.setId(builder.id);
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        deliveryAddress = builder.deliveryAddress;
        price = builder.price;
        items = builder.items;
        status = builder.status;
        trackingId = builder.trackingId;
        failureMessages = builder.failureMessages;
    }


    public CustomerId getCustomerId() {
        return customerId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public StreetAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public static final class Builder {
        private OrderId id;
        private final CustomerId customerId;
        private final RestaurantId restaurantId;
        private final StreetAddress deliveryAddress;
        private final Money price;
        private final List<OrderItem> items;
        private OrderStatus status;
        private TrackingId trackingId;
        private List<String> failureMessages;

        public Builder(CustomerId customerId, RestaurantId restaurantId, StreetAddress deliveryAddress, Money price, List<OrderItem> items) {
            this.customerId = customerId;
            this.restaurantId = restaurantId;
            this.deliveryAddress = deliveryAddress;
            this.price = price;
            this.items = items;
        }

        public Builder id(OrderId val) {
            id = val;
            return this;
        }

        public Builder status(OrderStatus val) {
            status = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
