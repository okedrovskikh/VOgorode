package ru.tinkoff.academy.landscape.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.landscape.user.dto.LandscapeUserCreateDto;
import ru.tinkoff.academy.landscape.user.dto.LandscapeUserUpdateDto;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface LandscapeUserMapper {
    @Mapping(target = "type", expression = "java(\"handyman\")")
    LandscapeUserCreateDto workerCreateDtoToUserCreateDto(WorkerCreateDto workerCreateDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", expression = "java(\"handyman\")")
    LandscapeUserUpdateDto workerUpdateDtoToUserUpdateDto(WorkerUpdateDto workerUpdateDto, UUID id);
}
