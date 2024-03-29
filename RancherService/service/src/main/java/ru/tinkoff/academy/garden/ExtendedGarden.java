package ru.tinkoff.academy.garden;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtendedGarden {
    private String id;
    private UUID ownerId;
    private UUID siteId;
    private Double x1;
    private Double y1;
    private Double x2;
    private Double y2;
    private Double square;
    private Double latitude;
    private Double longitude;
    private List<WorkEnum> works;
}
