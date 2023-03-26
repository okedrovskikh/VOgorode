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
    @Mapping(target = "latitude", expression = "java(this.fromStringToDouble(userCreateDto.getLatitude()))")
    @Mapping(target = "longitude", expression = "java(this.fromStringToDouble(userCreateDto.getLongitude()))")
    public abstract User dtoToUser(UserCreateDto userCreateDto);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "latitude", expression = "java(this.fromStringToDouble(userUpdateDto.getLatitude()))")
    @Mapping(target = "longitude", expression = "java(this.fromStringToDouble(userUpdateDto.getLongitude()))")
    public abstract User dtoToUser(UserUpdateDto userUpdateDto);

    protected final Double fromStringToDouble(String s) {
        if (s == null) {
            return null;
        }

        return Double.valueOf(s);
    }
}
