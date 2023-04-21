package ru.tinkoff.academy.fielder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tinkoff.academy.field.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "fielder")
@Table(schema = "public", catalog = "vogorode")
public class Fielder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "email", nullable = false)
    private String email;
    @OneToMany(mappedBy = "fielder")
    @JsonIgnoreProperties({"fielder"})
    private List<Field> fields;
    @Column(name = "telephone")
    private String telephone;

    public List<Field> getFields() {
        if (fields == null) {
            return new ArrayList<>();
        }
        return fields;
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Fielder fielder)) {
            return false;
        }

        return Objects.equals(id, fielder.id);
    }
}
