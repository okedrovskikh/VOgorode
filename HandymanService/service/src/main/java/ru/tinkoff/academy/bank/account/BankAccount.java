package ru.tinkoff.academy.bank.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.tinkoff.academy.bank.account.payment.system.PaymentSystem;
import ru.tinkoff.academy.user.User;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "bank_account")
public class BankAccount {
    @Id
    private String id;
    @DocumentReference
    @JsonIgnoreProperties({"accounts"})
    private User user;
    @Field(name = "card_id")
    private String cardId;
    @Field(name = "payment_system")
    private PaymentSystem paymentSystem;
    @Field(name = "bank")
    private String bank;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BankAccount bankAccount)) {
            return false;
        }

        return Objects.equals(id, bankAccount.id);
    }
}
