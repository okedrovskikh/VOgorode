package ru.tinkoff.academy.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
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
