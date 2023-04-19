package ru.tinkoff.academy.bank.account.dto;

import lombok.Data;
import ru.tinkoff.academy.bank.account.payment.system.PaymentSystem;

@Data
public class BankAccountCreateDto {
    private String cardId;
    private PaymentSystem paymentSystem;
}
