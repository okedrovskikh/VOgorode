package ru.tinkoff.academy.field.dto;

import lombok.Data;
import ru.tinkoff.academy.field.point.Point;

@Data
public class FieldCreateDto {
    private String address;
    private Double latitude;
    private Double longitude;
    private Point area;
    private Long fielderId;
}
