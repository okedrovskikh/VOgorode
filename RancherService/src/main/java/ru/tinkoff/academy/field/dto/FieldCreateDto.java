package ru.tinkoff.academy.field.dto;

import lombok.Data;

@Data
public class FieldCreateDto {
    private String address;
    private Double latitude;
    private Double longitude;
    private Object area;
}
