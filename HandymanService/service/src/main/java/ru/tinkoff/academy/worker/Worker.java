package ru.tinkoff.academy.worker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
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

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Worker worker)) {
            return false;
        }

        return Objects.equals(id, worker.id);
    }
}
