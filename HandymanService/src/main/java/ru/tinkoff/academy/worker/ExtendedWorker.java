package ru.tinkoff.academy.worker;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExtendedWorker {
    private Long id;
    private List<WorkEnum> works;
    private String login;
    private String email;
    private String telephone;
}
