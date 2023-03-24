package ru.tinkoff.academy.user;

import org.mapstruct.Mapper;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.user.dto.UserCreateDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserCreateDto workerCreateDtoToUserCreateDto(WorkerCreateDto workerCreateDto);
}
