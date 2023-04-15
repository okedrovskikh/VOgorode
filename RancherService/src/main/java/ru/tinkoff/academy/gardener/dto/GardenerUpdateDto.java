package ru.tinkoff.academy.gardener.dto;

import lombok.Data;

@Data
public class GardenerUpdateDto {
    private Long id;
    private String name;
    private String surname;
    private String telephone;
}
