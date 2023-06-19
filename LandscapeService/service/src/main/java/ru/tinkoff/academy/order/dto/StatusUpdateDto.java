package ru.tinkoff.academy.order.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import ru.tinkoff.academy.order.status.OrderStatus;

@Data
public class StatusUpdateDto {
    @NotNull
    private Long id;
    @NotNull
    private OrderStatus status;
}
