package ru.tinkoff.academy.field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Polygon;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import ru.tinkoff.academy.fielder.Fielder;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "field")
public class Field {
    @Id
    private String id;
    @org.springframework.data.mongodb.core.mapping.Field(name = "address")
    private String address;
    @org.springframework.data.mongodb.core.mapping.Field(name = "latitude")
    private Double latitude;
    @org.springframework.data.mongodb.core.mapping.Field(name = "longitude")
    private Double longitude;
    @org.springframework.data.mongodb.core.mapping.Field(name = "area")
    private Polygon area;
    @DocumentReference
    @org.springframework.data.mongodb.core.mapping.Field(name = "fielder_id")
    private Fielder fielder;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Field field)) {
            return false;
        }

        return Objects.equals(id, field.id);
    }
}
