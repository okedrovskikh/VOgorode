package ru.tinkoff.academy.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.tinkoff.academy.converter.UuidConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
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
@TypeDef(name = "user_enum_type", typeClass = UserEnumType.class)
public class User {
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false, columnDefinition = "uuid")
    @Convert(converter = UuidConverter.class)
    private UUID id;
    @Column(name = "u_type", nullable = false, columnDefinition = "user_types")
    @Enumerated(EnumType.STRING)
    @Type(type = "user_enum_type")
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
