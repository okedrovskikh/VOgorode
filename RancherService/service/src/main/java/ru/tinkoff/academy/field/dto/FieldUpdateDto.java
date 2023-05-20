package ru.tinkoff.academy.field.dto;

import lombok.Data;

@Data
public class FieldUpdateDto {
    private Long id;
    private String address;
    private Double latitude;
    private Double longitude;
    private String area;
}
