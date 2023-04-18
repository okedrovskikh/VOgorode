package ru.tinkoff.academy.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import ru.tinkoff.academy.account.payment.system.PaymentSystem;
import ru.tinkoff.academy.account.payment.system.PaymentSystemEnumType;
import ru.tinkoff.academy.user.User;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "account")
@Table(schema = "public", catalog = "vogorode")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"accounts"})
    private User user;
    @Column(name = "card_id", nullable = false)
    private String cardId;
    @Column(name = "payment_system", nullable = false)
    @Enumerated(EnumType.STRING)
    @Type(value = PaymentSystemEnumType.class)
    private PaymentSystem paymentSystem;

    @Override
    public int hashCode() {
        return id.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Account account)) {
            return false;
        }

        return Objects.equals(id, account.id);
    }
}
