package ru.tinkoff.academy.bank.account.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import ru.tinkoff.academy.bank.account.payment.system.PaymentSystem;

@Data
public class BankAccountCreateDto {
    @JsonAlias({"card_id"})
    private String cardId;
    @JsonAlias({"payment_system"})
    private PaymentSystem paymentSystem;
    private String bank;
}
