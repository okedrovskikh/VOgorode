package ru.tinkoff.academy.landscape.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LandscapeUserUpdateDto {
    private UUID id;
    private String type;
    private String login;
    private String email;
    private String telephone;
    private String latitude;
    private String longitude;
}
