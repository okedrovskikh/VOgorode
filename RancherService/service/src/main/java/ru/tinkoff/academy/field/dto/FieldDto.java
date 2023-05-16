package ru.tinkoff.academy.field.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.academy.fielder.Fielder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldDto {
    private String id;
    private String address;
    private Double latitude;
    private Double longitude;
    private double area;
    @JsonIgnoreProperties({"fields"})
    private Fielder fielder;
}
