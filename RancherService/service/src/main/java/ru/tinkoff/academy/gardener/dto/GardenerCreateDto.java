package ru.tinkoff.academy.gardener.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class GardenerCreateDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String email;
    private String telephone;
    private List<String> fieldsId;
}
