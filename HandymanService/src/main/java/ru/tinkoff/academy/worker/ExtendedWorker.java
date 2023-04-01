package ru.tinkoff.academy.worker;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ExtendedWorker {
    private String id;
    private UUID userId;
    private List<WorkEnum> works;
    private String login;
    private String email;
    private String telephone;
}
