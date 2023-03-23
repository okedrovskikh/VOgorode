package ru.tinkoff.academy.worker.dto;

import lombok.Data;
import ru.tinkoff.academy.worker.WorkEnum;

import java.util.List;

@Data
public class WorkerUpdateDto {
    private Long id;
    private List<WorkEnum> works;
}
