package dev.naikvarun.food.order.domain.entity;

import dev.naikvarun.food.common.domain.entity.BaseEntity;
import dev.naikvarun.food.common.domain.valueobject.MoneyBuilder;
import dev.naikvarun.food.common.domain.valueobject.OrderId;
import org.javamoney.moneta.Money;

public class OrderItem extends BaseEntity<OrderItemId> {
    private OrderId orderId;
    private final Product product;
    private final Integer quantity;
    private final Money price;
    private final Money subTotal;

    private OrderItem(Builder builder) {
        super.setId(builder.id);
        orderId = builder.orderId;
        product = builder.product;
        quantity = builder.quantity;
        price = builder.price;
        subTotal = builder.subTotal;
    }


    public OrderId getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Money getPrice() {
        return price;
    }

    public Money getSubTotal() {
        return subTotal;
    }

    public void initialize(OrderItemId orderItemId, OrderId orderId) {
        this.orderId = orderId;
        this.setId(orderItemId);
    }

    public boolean isPriceValid() {
        return this.price.isGreaterThan(MoneyBuilder.from(0))
                && this.price.isEqualTo(this.product.getPrice())
                && this.price.multiply(quantity).isEqualTo(this.getSubTotal());
    }

    public static final class Builder {
        private OrderItemId id;
        private OrderId orderId;
        private final Product product;
        private final Integer quantity;
        private final Money price;
        private final Money subTotal;

        public Builder(Product product, Integer quantity, Money price, Money subTotal) {
            this.product = product;
            this.quantity = quantity;
            this.price = price;
            this.subTotal = subTotal;
        }

        public Builder id(OrderItemId val) {
            id = val;
            return this;
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
