package ru.tinkoff.academy.landscape.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.academy.landscape.account.type.AccountType;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    private UUID id;
    private AccountType type;
    private String login;
    private String email;
    private String telephone;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private Double latitude;
    private Double longitude;
}
