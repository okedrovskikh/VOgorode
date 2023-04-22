package ru.tinkoff.academy.field.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.tinkoff.academy.fielder.Fielder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class FieldDto {
    private Long id;
    private String address;
    private Double latitude;
    private Double longitude;
    private double area;
    @JsonIgnoreProperties({"fields"})
    private Fielder fielder;
}
