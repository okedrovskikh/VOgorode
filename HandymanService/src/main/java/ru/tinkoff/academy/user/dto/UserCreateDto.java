package ru.tinkoff.academy.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateDto {
    private String type;
    private String login;
    private String email;
    private String telephone;
    private Double latitude;
    private Double longitude;
}
