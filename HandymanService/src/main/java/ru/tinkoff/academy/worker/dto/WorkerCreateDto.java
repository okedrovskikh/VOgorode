package ru.tinkoff.academy.worker.dto;

import lombok.Data;
import ru.tinkoff.academy.worker.WorkEnum;

import java.util.List;

@Data
public class WorkerCreateDto {
    private List<WorkEnum> works;
}
