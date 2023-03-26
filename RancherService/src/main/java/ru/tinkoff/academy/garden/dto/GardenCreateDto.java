package ru.tinkoff.academy.garden.dto;

import lombok.Data;
import ru.tinkoff.academy.garden.GardenWorks;

import java.util.List;
import java.util.UUID;

@Data
public class GardenCreateDto {
    private UUID ownerId;
    private Double x1;
    private Double y1;
    private Double x2;
    private Double y2;
    private List<GardenWorks> works;
}
