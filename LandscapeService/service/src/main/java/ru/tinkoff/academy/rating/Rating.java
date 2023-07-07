package ru.tinkoff.academy.rating;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "rating")
@Table(schema = "public", catalog = "vogorode")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "rating", nullable = false)
    @Range(min = 0, max = 10)
    private Integer rating;
    @Column(name = "description")
    private String description;
    @Column(name = "worker_id", nullable = false)
    private UUID workerId;
}
