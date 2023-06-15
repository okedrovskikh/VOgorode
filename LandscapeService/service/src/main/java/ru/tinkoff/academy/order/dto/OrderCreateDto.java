package ru.tinkoff.academy.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.UUID;

@Data
public class OrderCreateDto {
    @NotNull
    private UUID gardenId;
    @NotEmpty
    private WorkEnum[] works;
}
