package ru.tinkoff.academy.garden;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExtendedGarden {
    private Long id;
    private Long ownerId;
    private Double square;
    private Double latitude;
    private Double longitude;
    private List<GardenWorks> works;
}
