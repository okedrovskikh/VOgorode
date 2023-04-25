package ru.tinkoff.academy.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import ru.tinkoff.academy.bank.account.BankAccount;
import ru.tinkoff.academy.work.WorkEnum;
import ru.tinkoff.academy.work.WorkEnumArrayType;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "h_user")
@Table(schema = "public", catalog = "vogorode")
@NamedEntityGraph(name = "User.bankAccounts",
        attributeNodes = @NamedAttributeNode(value = "accounts"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "skills", nullable = false)
    @Type(value = WorkEnumArrayType.class)
    private WorkEnum[] skills;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "telephone", nullable = false)
    private String telephone;
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties({"user"})
    private List<BankAccount> accounts;
    @Column(name = "photo", nullable = false)
    private Byte[] photo;

    @Override
    public int hashCode() {
        return id.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User user)) {
            return false;
        }

        return Objects.equals(id, user.id);
    }
}
