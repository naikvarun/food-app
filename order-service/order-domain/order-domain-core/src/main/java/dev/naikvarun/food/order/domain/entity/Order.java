package dev.naikvarun.food.order.domain.entity;

import dev.naikvarun.food.common.domain.IdUtils;
import dev.naikvarun.food.common.domain.entity.AggregateRoot;
import dev.naikvarun.food.common.domain.valueobject.*;
import dev.naikvarun.food.order.domain.exceptions.OrderDomainException;
import dev.naikvarun.food.order.domain.valueobject.StreetAddress;
import dev.naikvarun.food.order.domain.valueobject.TrackingId;
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
    private List<String> failureMessages;

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


    public void initialize() {
        setId(new OrderId(IdUtils.getOrderId()));
        this.trackingId = new TrackingId(IdUtils.getEntityId("TRK"));
        this.status = OrderStatus.PENDING;
        initializeOrderItems();
    }

    private void initializeOrderItems() {
        this.items.forEach(orderItem -> orderItem.initialize(new OrderItemId(IdUtils.getEntityId("ITM")), this.getId()));
    }

    public void validate() {
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    private void validateItemsPrice() {
        Money orderTotal = items.stream().map(orderItem -> {
            this.validateOrderItem(orderItem);
            return orderItem.getSubTotal();
        }).reduce(MoneyBuilder.from(0), Money::add);
        if (this.price.compareTo(orderTotal) != 0) {
            throw new OrderDomainException("Total price " + this.price.getNumber() + " is not equal to order items total " + orderTotal.getNumber());
        }
    }

    private void validateOrderItem(OrderItem orderItem) {
        if (!orderItem.isPriceValid()) {
            throw new OrderDomainException("Order item " + orderItem.getId() + " is not valid");
        }
    }

    private void validateTotalPrice() {
        if (this.price == null || !price.isPositive()) {
            throw new OrderDomainException("Order total price should be greater than zero");
        }
    }

    private void validateInitialOrder() {
        if (this.status == null || this.getId() == null) {
            throw new OrderDomainException("Order is not in correct state for initialization");
        }
    }

    public void pay() {
        if (this.status != OrderStatus.PENDING) {
            throw new OrderDomainException("Order is not in correct state for payment");
        }
        this.status = OrderStatus.PAID;
    }

    public void approve() {
        if (this.status != OrderStatus.PAID) {
            throw new OrderDomainException("Order is not in correct state for approval");
        }
        this.status = OrderStatus.ACCEPTED;
    }

    public void initCancellation(List<String> failureMessages) {
        if (this.status != OrderStatus.PAID) {
            throw new OrderDomainException("Order is not in correct state to init cancellation");
        }
        this.status = OrderStatus.CANCELLING;
        this.updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if (this.failureMessages != null && !this.failureMessages.isEmpty()) {
            this.failureMessages.addAll(
                    failureMessages.stream().filter(message -> !message.isEmpty()).toList()
            );
        }
        if (this.failureMessages == null) {
            this.failureMessages = failureMessages;
        }
    }

    public void cancel(List<String> failureMessages) {
        if (this.status != OrderStatus.PENDING && this.status != OrderStatus.CANCELLING) {
            throw new OrderDomainException("Order is not in correct state for cancellation");
        }
        this.status = OrderStatus.CANCELLED;
        this.updateFailureMessages(failureMessages);
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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private OrderId id;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private StreetAddress deliveryAddress;
        private Money price;
        private List<OrderItem> items;
        private OrderStatus status;
        private TrackingId trackingId;
        private List<String> failureMessages;

        private Builder() {
        }


        public Builder withId(OrderId val) {
            id = val;
            return this;
        }

        public Builder withCustomerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder withRestaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder withDeliveryAddress(StreetAddress val) {
            deliveryAddress = val;
            return this;
        }

        public Builder withPrice(Money val) {
            price = val;
            return this;
        }

        public Builder withItems(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder withStatus(OrderStatus val) {
            status = val;
            return this;
        }

        public Builder withTrackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder withFailureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
