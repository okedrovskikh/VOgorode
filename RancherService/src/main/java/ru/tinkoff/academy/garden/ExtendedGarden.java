package ru.tinkoff.academy.garden;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExtendedGarden {
    private String id;
    private Long landscapeId;
    private Long ownerId;
    private Double x1;
    private Double y1;
    private Double x2;
    private Double y2;
    private Double square;
    private Double latitude;
    private Double longitude;
    private List<GardenWorks> works;
}
