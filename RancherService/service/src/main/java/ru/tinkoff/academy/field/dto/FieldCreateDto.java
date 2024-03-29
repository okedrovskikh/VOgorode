package ru.tinkoff.academy.field.dto;

import lombok.Data;

import java.util.List;

@Data
public class FieldCreateDto {
    private String address;
    private Double latitude;
    private Double longitude;
    private List<Double> area;
}
