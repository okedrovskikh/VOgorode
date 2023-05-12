package ru.tinkoff.academy.worker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "worker")
public class Worker {
    @Id
    private String id;
    private UUID landscapeUserId;
    private List<WorkEnum> services;
    private Double latitude;
    private Double longitude;
}
