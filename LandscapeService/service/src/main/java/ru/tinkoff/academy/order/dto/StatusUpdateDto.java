package ru.tinkoff.academy.order.dto;

import lombok.Data;
import ru.tinkoff.academy.order.status.OrderStatus;

@Data
public class StatusUpdateDto {
    private Long id;
    private OrderStatus status;
}
