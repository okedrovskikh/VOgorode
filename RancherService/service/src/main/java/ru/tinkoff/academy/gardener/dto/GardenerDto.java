package ru.tinkoff.academy.gardener.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.academy.field.dto.FieldDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GardenerDto {
    private String id;
    private String name;
    private String surname;
    private String email;
    @JsonIgnoreProperties({"gardener"})
    private List<FieldDto> fields;
    private String telephone;
}
