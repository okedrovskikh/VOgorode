package ru.tinkoff.academy.worker;

import lombok.Builder;
import lombok.Data;
import ru.tinkoff.academy.landscape.user.LandscapeUser;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ExtendedByUserWorker {
    private String id;
    private UUID userId;
    private List<WorkEnum> services;
    private Double latitude;
    private Double longitude;

    private LandscapeUser landscapeUser;
}
