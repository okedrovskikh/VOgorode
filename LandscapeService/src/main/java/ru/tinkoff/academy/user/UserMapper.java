package ru.tinkoff.academy.user;

import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.tinkoff.academy.user.dto.UserCreateDto;
import ru.tinkoff.academy.user.dto.UserUpdateDto;

import java.sql.Timestamp;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", expression = "java(java.sql.Timestamp.from(java.time.Instant.now()))")
    @Mapping(target = "updateDate", ignore = true)
    User dtoToUser(UserCreateDto userCreateDto);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updateDate", expression = "java(java.sql.Timestamp.from(java.time.Instant.now()))")
    User dtoToUser(UserUpdateDto userUpdateDto);
}
