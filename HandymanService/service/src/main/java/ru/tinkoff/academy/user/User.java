package ru.tinkoff.academy.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.tinkoff.academy.bank.account.BankAccount;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "user")
@CompoundIndexes({
        @CompoundIndex(name = "user_email_and_telephone_constraint", def = "{ 'email': 1, 'telephone': 1 }", unique = true)
})
public class User {
    @Id
    private String id;
    @Field(name = "name")
    private String name;
    @Field(name = "surname")
    private String surname;
    @Field(name = "skills")
    private WorkEnum[] skills;
    @Field(name = "email")
    private String email;
    @Field(name = "telephone")
    private String telephone;
    @DocumentReference
    @JsonIgnoreProperties({"user"})
    private List<BankAccount> accounts;
    @Field(name = "photo")
    private Byte[] photo;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User user)) {
            return false;
        }

        return Objects.equals(id, user.id);
    }
}
