package ru.tinkoff.academy.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.user.dto.UserCreateDto;
import ru.tinkoff.academy.user.dto.UserUpdateDto;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Mapping(target = "id", ignore = true)
    public abstract User dtoToUser(UserCreateDto createDto);

    public User updateUser(User user, UserUpdateDto updateDto) {
        user.setName(updateDto.getName());
        user.setSurname(updateDto.getSurname());
        user.setSkills(updateDto.getSkills());
        user.setEmail(updateDto.getEmail());
        user.setTelephone(updateDto.getTelephone());
        user.setPhoto(updateDto.getPhoto());
        return user;
    }
}
