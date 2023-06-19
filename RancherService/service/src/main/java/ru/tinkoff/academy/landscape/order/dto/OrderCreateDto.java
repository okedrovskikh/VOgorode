package ru.tinkoff.academy.landscape.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class OrderCreateDto {
    @NotNull
    private UUID gardenId;
    @NotEmpty
    private List<WorkEnum> works;
}
