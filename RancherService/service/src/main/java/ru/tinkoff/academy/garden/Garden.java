package ru.tinkoff.academy.garden;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Document(collection = "garden")
public class Garden {
    @Id
    private String id;
    private UUID ownerId;
    private UUID siteId;
    private Double x1;
    private Double y1;
    private Double x2;
    private Double y2;
    private Double square;
    private List<WorkEnum> works;
}