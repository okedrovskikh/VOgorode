package ru.tinkoff.academy.account.dto;

import lombok.Data;
import ru.tinkoff.academy.account.PaymentSystem;

@Data
public class AccountUpdateDto {
    private Long id;
    private Long userId;
    private String cardId;
    private PaymentSystem paymentSystem;
}
