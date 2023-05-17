package ru.tinkoff.academy.landscape.account.dto;

import lombok.Builder;
import lombok.Data;
import ru.tinkoff.academy.landscape.account.type.AccountType;

@Data
@Builder
public class AccountCreateDto {
    private AccountType type;
    private String login;
    private String email;
    private String telephone;
    private String latitude;
    private String longitude;
}
