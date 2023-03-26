package ru.tinkoff.academy.worker.dto;

import lombok.Data;
import ru.tinkoff.academy.worker.WorkEnum;

import java.util.List;
import java.util.UUID;

@Data
public class WorkerUpdateDto {
    private UUID login;
    private String email;
    private String telephone;
    private String id;
    private List<WorkEnum> works;
    private Double latitude;
    private Double longitude;
}
