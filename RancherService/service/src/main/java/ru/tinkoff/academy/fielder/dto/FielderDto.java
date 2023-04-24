package ru.tinkoff.academy.fielder.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.tinkoff.academy.field.dto.FieldDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class FielderDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    @JsonIgnoreProperties({"fielder"})
    private List<FieldDto> fields;
    private String telephone;
}
