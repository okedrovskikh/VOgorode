package ru.tinkoff.academy.fielder.dto;

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
public class FielderDto {
    private String id;
    private String name;
    private String surname;
    private String email;
    @JsonIgnoreProperties({"fielder"})
    private List<FieldDto> fields;
    private String telephone;
}
