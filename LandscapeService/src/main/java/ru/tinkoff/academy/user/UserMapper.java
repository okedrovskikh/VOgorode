package ru.tinkoff.academy.user;

import org.mapstruct.Mapper;
import ru.tinkoff.academy.user.dto.UserCreateDto;
import ru.tinkoff.academy.user.dto.UserUpdateDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User dtoToUser(UserCreateDto userCreateDto);

    User dtoToUser(UserUpdateDto userUpdateDto);
}
