package ru.tinkoff.academy.user;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class User {
    private Long id;
    private String type;
    private String login;
    private String email;
    private String telephone;
    private Timestamp creationDate;
    private Timestamp updateDate;
}
