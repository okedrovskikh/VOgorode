package ru.tinkoff.academy.worker.dto;

import lombok.Data;
import ru.tinkoff.academy.worker.WorkEnum;

import java.util.List;

@Data
public class WorkerCreateDto {
    private String type;
    private String login;
    private String email;
    private String telephone;
    private List<WorkEnum> works;
}
