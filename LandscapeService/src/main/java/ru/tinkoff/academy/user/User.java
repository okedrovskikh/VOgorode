package ru.tinkoff.academy.user;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import ru.tinkoff.academy.converter.UuidConverter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "users")
@Table(schema = "public", catalog = "vogorode")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true, updatable = false, columnDefinition = "uuid")
    @Convert(converter = UuidConverter.class)
    private UUID id;
    @Column(name = "u_type", nullable = false, columnDefinition = "user_types")
    @Enumerated(EnumType.STRING)
    @Type(value = UserEnumType.class)
    private UserType type;
    @Column(name = "u_login", nullable = false)
    private String login;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "telephone", nullable = false)
    private String telephone;
    @Column(name = "creation_date", nullable = false)
    private Timestamp creationDate;
    @Column(name = "update_date", nullable = false)
    private Timestamp updateDate;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;

    @PrePersist
    public void prePersist() {
        this.creationDate = Timestamp.from(Instant.now());
        this.updateDate = creationDate;
    }

    @PreUpdate
    public void updateDate() {
        this.updateDate = Timestamp.from(Instant.now());
    }
}
