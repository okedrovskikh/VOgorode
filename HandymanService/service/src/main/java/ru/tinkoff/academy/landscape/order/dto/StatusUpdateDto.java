package ru.tinkoff.academy.landscape.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.tinkoff.academy.landscape.order.status.OrderStatus;

@Data
@AllArgsConstructor
@Builder
public class StatusUpdateDto {
    private Long id;
    private OrderStatus status;
}
