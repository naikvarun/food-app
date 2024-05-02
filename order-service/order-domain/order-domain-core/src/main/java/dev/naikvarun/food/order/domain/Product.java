package dev.naikvarun.food.order.domain;

import dev.naikvarun.food.common.domain.IdUtils;
import dev.naikvarun.food.common.domain.entity.BaseEntity;
import dev.naikvarun.food.common.domain.valueobject.ProductId;
import org.javamoney.moneta.Money;

public class Product extends BaseEntity<ProductId> {
    private final String name;
    private final Money price;

    public Product(ProductId productId ,String name, Money price) {
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
}
