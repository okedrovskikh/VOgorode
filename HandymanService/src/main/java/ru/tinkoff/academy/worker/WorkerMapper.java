package ru.tinkoff.academy.worker;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.landscape.user.LandscapeUser;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;

@Mapper(componentModel = "spring")
public interface WorkerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "landscapeUserId", ignore = true)
    Worker dtoToWorker(WorkerCreateDto workerCreateDto);

    @Mapping(target = "landscapeUserId", ignore = true)
    Worker dtoToWorker(WorkerUpdateDto workerCreateDto);

    @Mapping(target = "id", source = "worker.id")
    @Mapping(target = "landscapeUser", source = "landscapeUser")
    @Mapping(target = "latitude", source = "worker.latitude")
    @Mapping(target = "longitude", source = "worker.longitude")
    ExtendedByUserWorker toExtendedWorker(Worker worker, LandscapeUser landscapeUser);
}
