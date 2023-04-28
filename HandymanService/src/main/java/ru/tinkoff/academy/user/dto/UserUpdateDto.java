package ru.tinkoff.academy.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserUpdateDto {
    private UUID id;
    private String type;
    private String login;
    private String email;
    private String telephone;
    private String latitude;
    private String longitude;
}
