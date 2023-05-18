package ru.tinkoff.academy.account.dto;

import lombok.Data;
import ru.tinkoff.academy.account.type.AccountType;

import java.util.UUID;

@Data
public class AccountUpdateDto {
    private UUID id;
    private AccountType type;
    private String login;
    private String email;
    private String telephone;
    private String latitude;
    private String longitude;
}
