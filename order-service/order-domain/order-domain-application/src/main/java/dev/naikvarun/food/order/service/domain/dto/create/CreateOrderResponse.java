package dev.naikvarun.food.order.service.domain.dto.create;

import dev.naikvarun.food.common.domain.valueobject.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CreateOrderResponse {
    @NotNull
    private final String orderId;
    @NotNull
    private final OrderStatus status;
    @NotNull
    private final String message;
}
