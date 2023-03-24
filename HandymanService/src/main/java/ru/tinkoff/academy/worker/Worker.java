package ru.tinkoff.academy.worker;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Worker {
    private Long id;
    private List<WorkEnum> works;
}