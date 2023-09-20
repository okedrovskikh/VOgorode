package ru.tinkoff.academy.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "job")
@CompoundIndexes(
        @CompoundIndex(name = "order_id_constraint", def = "{ order_id : 1 }", unique = true)
)
public class Job {
    @Id
    private String id;
    @Field(name = "status")
    private JobStatus status;
    @Field(name = "order_id")
    private Long orderId;
    @Field(name = "workers_id")
    private List<String> workersId;


    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Job job)) {
            return false;
        }

        return Objects.equals(id, job.id);
    }
}
