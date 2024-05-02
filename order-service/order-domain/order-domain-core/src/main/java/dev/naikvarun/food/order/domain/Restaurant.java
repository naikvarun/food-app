package dev.naikvarun.food.order.domain;

import dev.naikvarun.food.common.domain.entity.AggregateRoot;
import dev.naikvarun.food.common.domain.valueobject.RestaurantId;

import java.util.List;

public class Restaurant extends AggregateRoot<RestaurantId> {
    private final List<Product> products;
    private boolean active;

    private Restaurant(Builder builder) {
        super.setId(builder.id);
        products = builder.products;
        active = builder.active;
    }


    public List<Product> getProducts() {
        return products;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private RestaurantId id;
        private List<Product> products;
        private boolean active;

        public Builder() {

        }

        public Builder id(RestaurantId val) {
            id = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(this);
        }
    }
}
