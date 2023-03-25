package ru.tinkoff.academy.user.dto;

import lombok.Data;

@Data
public class UserCreateDto {
    private String type;
    private String login;
    private String email;
    private String telephone;
    private String latitude;
    private String longitude;
}
