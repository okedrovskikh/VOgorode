package ru.tinkoff.academy.field.dto;

import lombok.Data;

import java.util.List;

@Data
public class FieldUpdateDto {
    private String id;
    private String address;
    private Double latitude;
    private Double longitude;
    private List<Double> area;
}
