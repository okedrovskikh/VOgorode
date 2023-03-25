package ru.tinkoff.academy.garden.dto;

import lombok.Data;
import ru.tinkoff.academy.garden.GardenWorks;

import java.util.List;

@Data
public class GardenUpdateDto {
    private String id;
    private Long landscapeId;
    private Long ownerId;
    private Double x1;
    private Double y1;
    private Double x2;
    private Double y2;
    private List<GardenWorks> works;
}
