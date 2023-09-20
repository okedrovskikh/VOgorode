package ru.tinkoff.academy.garden;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
@AllArgsConstructor
@NoArgsConstructor
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

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Garden garden)) {
            return false;
        }

        return Objects.equals(id, garden.id);
    }
}
