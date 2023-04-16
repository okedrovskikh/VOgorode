package ru.tinkoff.academy.user;

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
import ru.tinkoff.academy.account.Account;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "h_user")
@Table(schema = "public", catalog = "vogorode")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "skills", nullable = false)
    private String[] skills;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "telephone", nullable = false)
    private String telephone;
    @OneToMany(mappedBy = "user")
    private List<Account> accountList;
    @Column(name = "photo", nullable = false)
    private Byte[] photo;
}
