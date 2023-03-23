package ru.tinkoff.academy.garden.dto;

import lombok.Data;
import ru.tinkoff.academy.garden.GardenWorks;

import java.util.List;

@Data
public class GardenCreateDto {
    private Long ownerId;
    private Double square;
    private Double latitude;
    private Double longitude;
    private List<GardenWorks> works;
}
