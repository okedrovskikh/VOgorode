package ru.tinkoff.academy.landscape.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.List;
import java.util.UUID;

@Data
public class OrderCreateDto {
    @NotNull
    private UUID gardenId;
    @NotEmpty
    private List<WorkEnum> works;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
}
