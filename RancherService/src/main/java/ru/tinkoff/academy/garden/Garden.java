package ru.tinkoff.academy.garden;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "garden")
public class Garden {
    @Id
    private String id;
    @JsonIgnore
    private Long landscapeId;
    private Long ownerId;
    private Double x1;
    private Double y1;
    private Double x2;
    private Double y2;
    private Double square;
    private List<GardenWorks> works;
}
