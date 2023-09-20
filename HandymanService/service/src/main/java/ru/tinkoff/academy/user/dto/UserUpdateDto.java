package ru.tinkoff.academy.user.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.List;

@Data
public class UserUpdateDto {
    private String id;
    private String name;
    private String surname;
    private WorkEnum[] skills;
    private String email;
    private String telephone;
    @JsonAlias({"accounts_id"})
    private List<String> accountsId;
    private Byte[] photo;
}
