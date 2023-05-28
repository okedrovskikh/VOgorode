package ru.tinkoff.academy.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.tinkoff.academy.order.status.OrderStatus;
import ru.tinkoff.academy.work.WorkEnum;

@Data
public class OrderUpdateDto {
    @NotNull
    private Long id;
    @NotEmpty
    private WorkEnum[] works;
}
