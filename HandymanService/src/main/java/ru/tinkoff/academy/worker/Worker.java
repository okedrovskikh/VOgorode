package ru.tinkoff.academy.worker;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "worker")
public class Worker {
    @Id
    private String id;
    private Long landscapeId;
    private List<WorkEnum> works;
}
