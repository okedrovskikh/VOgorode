package ru.tinkoff.academy.handyman.worker;

import lombok.Data;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.UUID;
import java.util.List;

@Data
public class Worker {
    private String id;
    private UUID landscapeId;
    private List<WorkEnum> services;
    private double latitude;
    private double longitude;
}
