package ru.tinkoff.academy.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.user.dto.UserCreateDto;
import ru.tinkoff.academy.user.dto.UserUpdateDto;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    public abstract User dtoToUser(UserCreateDto userCreateDto);

    public User updateUser(UserUpdateDto userUpdateDto, User user) {
        if (userUpdateDto.getType() != null) {
            user.setType(userUpdateDto.getType());
        }

        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }

        if (userUpdateDto.getLogin() != null) {
            user.setLogin(userUpdateDto.getLogin());
        }

        if (userUpdateDto.getTelephone() != null) {
            user.setTelephone(userUpdateDto.getTelephone());
        }

        if (userUpdateDto.getLatitude() != null) {
            user.setLatitude(Double.valueOf(userUpdateDto.getLatitude()));
        }

        if (userUpdateDto.getLongitude() != null) {
            user.setLongitude(Double.valueOf(userUpdateDto.getLongitude()));
        }

        return user;
    }
}
