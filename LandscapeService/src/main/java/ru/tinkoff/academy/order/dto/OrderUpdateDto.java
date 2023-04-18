package ru.tinkoff.academy.order.dto;

import lombok.Data;
import ru.tinkoff.academy.order.status.OrderStatus;
import ru.tinkoff.academy.work.WorkEnum;

@Data
public class OrderUpdateDto {
    private Long id;
    private Long gardenId;
    private Long userId;
    private WorkEnum[] works;
    private OrderStatus status;
}
