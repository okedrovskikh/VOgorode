package ru.tinkoff.academy.field.dto;

import lombok.Data;
import org.springframework.data.geo.Point;

@Data
public class FieldCreateDto {
    private String address;
    private Double latitude;
    private Double longitude;
    private Point area;
    private Long fielderId;
}
