package ru.tinkoff.academy.fielder.dto;

import lombok.Data;

import java.util.List;

@Data
public class FielderUpdateDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private List<Long> fieldsId;
}
