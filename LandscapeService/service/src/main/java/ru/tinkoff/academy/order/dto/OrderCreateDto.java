package ru.tinkoff.academy.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.tinkoff.academy.work.WorkEnum;

@Data
public class OrderCreateDto {
    @NotNull
    private Long gardenId;
    @NotNull
    private Long workerId;
    @NotEmpty
    private WorkEnum[] works;
}
