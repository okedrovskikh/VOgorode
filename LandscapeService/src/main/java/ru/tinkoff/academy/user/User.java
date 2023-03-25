package ru.tinkoff.academy.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "users")
@Table(schema = "public", catalog = "vogorode")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "u_type", nullable = false)
    private String type;
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
