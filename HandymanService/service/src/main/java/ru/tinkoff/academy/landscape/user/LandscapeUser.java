package ru.tinkoff.academy.landscape.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LandscapeUser {
    private UUID id;
    private String type;
    private String login;
    private String email;
    private String telephone;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private Double latitude;
    private Double longitude;
}
