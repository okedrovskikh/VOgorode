package ru.tinkoff.academy.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.user.dto.UserUpdateDto;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.user.dto.UserCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "type", expression = "java(\"HANDYMAN\")")
    UserCreateDto workerCreateDtoToUserCreateDto(WorkerCreateDto workerCreateDto);

    @Mapping(target = "type", expression = "java(\"HANDYMAN\")")
    UserUpdateDto workerUpdateDtoToUserUpdateDto(WorkerUpdateDto workerUpdateDto);
}
