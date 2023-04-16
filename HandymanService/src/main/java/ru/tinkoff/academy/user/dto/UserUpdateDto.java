package ru.tinkoff.academy.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserUpdateDto {
    private Long id;
    private String name;
    private String surname;
    private String[] skills;
    private String email;
    private String telephone;
    private List<Long> accountsId;
    private Byte[] photo;
}
