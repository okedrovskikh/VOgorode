package ru.tinkoff.academy.fielder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "gardener")
public class Fielder {
    @Id
    private String id;
    @Field(name = "name")
    private String name;
    @Field(name = "surname")
    private String surname;
    @Field(name = "email")
    private String email;
    @DocumentReference
    @Field(name = "fields_id")
    @JsonIgnoreProperties({"fielder"})
    private List<ru.tinkoff.academy.field.Field> fields;
    @Field(name = "telephone")
    private String telephone;

    public List<ru.tinkoff.academy.field.Field> getFields() {
        if (fields == null) {
            fields = new ArrayList<>();
        }

        return fields;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Fielder fielder)) {
            return false;
        }

        return Objects.equals(id, fielder.id);
    }
}
