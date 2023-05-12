package ru.tinkoff.academy.worker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.academy.landscape.user.LandscapeUser;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtendedByUserWorker {
    private String id;
    private List<WorkEnum> services;
    private Double latitude;
    private Double longitude;

    private LandscapeUser landscapeUser;
}
