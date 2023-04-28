package ru.tinkoff.academy.worker;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;
import ru.tinkoff.academy.user.User;

@Mapper(componentModel = "spring")
public interface WorkerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    Worker dtoToWorker(WorkerCreateDto workerCreateDto);

    @Mapping(target = "userId", ignore = true)
    Worker dtoToWorker(WorkerUpdateDto workerCreateDto);

    @Mapping(target = "id", source = "worker.id")
    @Mapping(target = "user", source = "user")
    ExtendedByUserWorker toExtendedWorker(Worker worker, User user);
}
