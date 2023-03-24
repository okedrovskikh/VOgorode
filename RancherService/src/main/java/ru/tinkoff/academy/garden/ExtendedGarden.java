package ru.tinkoff.academy.garden;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExtendedGarden {
    private Long id;
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
