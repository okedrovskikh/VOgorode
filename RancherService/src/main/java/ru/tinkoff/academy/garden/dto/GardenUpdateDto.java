package ru.tinkoff.academy.garden.dto;

import lombok.Data;
import ru.tinkoff.academy.garden.GardenWorks;

import java.util.List;

@Data
public class GardenUpdateDto {
    private Long id;
    private Long ownerId;
    private Double x1;
    private Double y1;
    private Double x2;
    private Double y2;
    private Double square;
    private List<GardenWorks> works;
}
