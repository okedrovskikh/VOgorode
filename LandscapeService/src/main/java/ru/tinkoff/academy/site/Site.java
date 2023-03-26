package ru.tinkoff.academy.site;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tinkoff.academy.converter.UuidConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "site")
@Table(schema = "public", catalog = "vogorode")
public class Site {
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false, columnDefinition = "uuid")
    @Convert(converter = UuidConverter.class)
    private UUID id;
    @Column(name = "latitude", nullable = false)
    private Double latitude;
    @Column(name = "longitude", nullable = false)
    private Double longitude;
}
