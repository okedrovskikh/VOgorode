package ru.tinkoff.academy.worker;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExtendedWorker {
    private String id;
    private String landscapeId;
    private List<WorkEnum> works;
    private String login;
    private String email;
    private String telephone;
}
