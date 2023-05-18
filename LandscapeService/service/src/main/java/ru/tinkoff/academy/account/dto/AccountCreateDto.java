package ru.tinkoff.academy.account.dto;

import lombok.Data;
import ru.tinkoff.academy.account.type.AccountType;

@Data
public class AccountCreateDto {
    private AccountType type;
    private String login;
    private String email;
    private String telephone;
    private String latitude;
    private String longitude;
}
