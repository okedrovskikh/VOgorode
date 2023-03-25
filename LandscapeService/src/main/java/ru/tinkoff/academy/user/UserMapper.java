package ru.tinkoff.academy.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.user.dto.UserCreateDto;
import ru.tinkoff.academy.user.dto.UserUpdateDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "latitude", expression = "java(java.lang.Double.valueOf(userCreateDto.getLatitude()))")
    @Mapping(target = "longitude", expression = "java(java.lang.Double.valueOf(userCreateDto.getLongitude()))")
    User dtoToUser(UserCreateDto userCreateDto);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "latitude", expression = "java(java.lang.Double.valueOf(userUpdateDto.getLatitude()))")
    @Mapping(target = "longitude", expression = "java(java.lang.Double.valueOf(userUpdateDto.getLongitude()))")
    User dtoToUser(UserUpdateDto userUpdateDto);
}
