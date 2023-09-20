package ru.tinkoff.academy.service.status;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceStatus {
    private String host;
    private String status;
    private String artifact;
    private String name;
    private String group;
    private String version;
}
