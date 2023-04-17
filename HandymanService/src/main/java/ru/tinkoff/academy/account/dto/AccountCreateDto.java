package ru.tinkoff.academy.account.dto;

import lombok.Data;
import ru.tinkoff.academy.account.PaymentSystem;

@Data
public class AccountCreateDto {
    private Long userId;
    private String cardId;
    private PaymentSystem paymentSystem;
}