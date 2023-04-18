package ru.tinkoff.academy.account.dto;

import lombok.Data;
import ru.tinkoff.academy.account.payment.system.PaymentSystem;

@Data
public class AccountUpdateDto {
    private Long id;
    private Long userId;
    private String cardId;
    private PaymentSystem paymentSystem;
}
