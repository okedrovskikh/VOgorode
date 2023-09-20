package ru.tinkoff.academy.gardener.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class GardenerUpdateDto {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String telephone;
    @JsonAlias({"fields_id"})
    private List<String> fieldsId;
}
