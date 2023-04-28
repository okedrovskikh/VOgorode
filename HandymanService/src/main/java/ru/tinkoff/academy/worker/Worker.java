package ru.tinkoff.academy.worker;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Document(collection = "worker")
public class Worker {
    @Id
    private String id;
    private UUID userId;
    private List<WorkEnum> services;
    private Double latitude;
    private Double longitude;
}
