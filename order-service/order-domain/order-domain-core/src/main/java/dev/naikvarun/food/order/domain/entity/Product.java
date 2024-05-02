package dev.naikvarun.food.order.domain.entity;

import dev.naikvarun.food.common.domain.entity.BaseEntity;
import dev.naikvarun.food.common.domain.valueobject.ProductId;
import org.javamoney.moneta.Money;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId, String name, Money price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public void updateWithConfirmedNameAndPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }
}
