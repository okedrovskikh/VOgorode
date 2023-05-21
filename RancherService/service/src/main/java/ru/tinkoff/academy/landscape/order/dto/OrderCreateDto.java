package ru.tinkoff.academy.landscape.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.List;

@Data
public class OrderCreateDto {
    @NotNull
    private String gardenId;
    @NotEmpty
    private List<WorkEnum> works;
}
