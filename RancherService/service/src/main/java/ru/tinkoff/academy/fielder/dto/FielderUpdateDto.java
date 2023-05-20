package ru.tinkoff.academy.fielder.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class FielderUpdateDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String telephone;
    @JsonAlias({"fields_id"})
    private List<Long> fieldsId;
}
