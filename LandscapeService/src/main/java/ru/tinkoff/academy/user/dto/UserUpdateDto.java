package ru.tinkoff.academy.user.dto;

import lombok.Data;
import ru.tinkoff.academy.user.UserType;

@Data
public class UserUpdateDto {
    private String id;
    private UserType type;
    private String login;
    private String email;
    private String telephone;
    private String latitude;
    private String longitude;
}
