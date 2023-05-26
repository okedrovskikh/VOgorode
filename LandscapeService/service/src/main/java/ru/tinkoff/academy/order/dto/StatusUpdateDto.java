package ru.tinkoff.academy.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.tinkoff.academy.order.status.OrderStatus;

@Data
@AllArgsConstructor
public class StatusUpdateDto {
    private Long id;
    private OrderStatus status;
}
