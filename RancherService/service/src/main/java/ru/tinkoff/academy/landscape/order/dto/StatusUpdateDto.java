package ru.tinkoff.academy.landscape.order.dto;

import lombok.Data;
import ru.tinkoff.academy.landscape.order.status.OrderStatus;

@Data
public class StatusUpdateDto {
    private Long id;
    private OrderStatus status;
}
