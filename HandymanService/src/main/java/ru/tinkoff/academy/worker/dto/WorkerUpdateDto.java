package ru.tinkoff.academy.worker.dto;

import lombok.Data;
import ru.tinkoff.academy.worker.WorkEnum;

import java.util.List;

@Data
public class WorkerUpdateDto {
    private String login;
    private String email;
    private String telephone;
    private String id;
    private List<WorkEnum> services;
    private Double latitude;
    private Double longitude;
}
