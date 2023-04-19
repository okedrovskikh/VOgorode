package ru.tinkoff.academy.account;

import jakarta.persistence.Column;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import ru.tinkoff.academy.account.type.AccountEnumType;
import ru.tinkoff.academy.account.type.AccountType;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "account")
@Table(schema = "public", catalog = "vogorode")
@EqualsAndHashCode
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true, updatable = false, columnDefinition = "uuid")
    private UUID id;
    @Column(name = "u_type", nullable = false, columnDefinition = "user_types")
    @Enumerated(EnumType.STRING)
    @Type(value = AccountEnumType.class)
    private AccountType type;
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
