package ru.tinkoff.academy.worker;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;
import ru.tinkoff.academy.user.User;

@Mapper(componentModel = "spring")
public interface WorkerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "landscapeId", source = "user.id")
    Worker toWorker(WorkerCreateDto workerCreateDto, User user);

    Worker dtoToWorker(WorkerUpdateDto workerCreateDto);

    @Mapping(target = "id", source = "worker.id")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "login", source = "user.login")
    @Mapping(target = "telephone", source = "user.telephone")
    ExtendedWorker toExtendedWorker(Worker worker, User user);
}
