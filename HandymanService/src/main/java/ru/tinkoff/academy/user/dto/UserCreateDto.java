package ru.tinkoff.academy.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserCreateDto {
    private String name;
    private String surname;
    private String[] skills;
    private String email;
    private String telephone;
    private List<Long> accountsId;
    private Byte[] photo;
}
