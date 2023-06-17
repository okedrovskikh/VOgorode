package ru.tinkoff.academy.landscape.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.tinkoff.academy.landscape.order.status.OrderStatus;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class WorkerUpdateDto {
    private Long id;
    private UUID workerId;
    private OrderStatus status;
}
