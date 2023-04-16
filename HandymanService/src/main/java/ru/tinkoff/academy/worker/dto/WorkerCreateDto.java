package ru.tinkoff.academy.worker.dto;

import lombok.Data;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.List;

@Data
public class WorkerCreateDto {
    private String login;
    private String email;
    private String telephone;
    private List<WorkEnum> services;
    private Double latitude;
    private Double longitude;
}
