package dev.naikvarun.food.order.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class CreateOrderCommand {
    @NotNull
    private final String customerId;
    @NotNull
    private final String restaurantId;
    @NotNull
    private final BigDecimal price;
    @NotNull
    private final List<OrderItem> item;
    @NotNull
    private final OrderAddress address;
}
