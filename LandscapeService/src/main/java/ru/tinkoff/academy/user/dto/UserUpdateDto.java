package ru.tinkoff.academy.user.dto;

import lombok.Data;

@Data
public class UserUpdateDto {
    private Long id;
    private String type;
    private String login;
    private String email;
    private String telephone;
}
