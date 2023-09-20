package ru.tinkoff.academy.gardener.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
public class GardenerUpdateDto {
    @NotNull
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String email;
    @NotEmpty
    private String telephone;
    private List<String> fieldsId;
}
