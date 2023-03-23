package ru.tinkoff.academy.user;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private Long id;
    private String type;
    private String login;
    private String email;
    private String telephone;
    private Timestamp creationDate;
    private Timestamp updateDate;
}
