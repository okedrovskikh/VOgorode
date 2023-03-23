package ru.tinkoff.academy.worker;

import org.mapstruct.Mapper;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;

@Mapper(componentModel = "spring")
public interface WorkerMapper {
    Worker dtoToWorker(WorkerCreateDto workerCreateDto);

    Worker dtoToWorker(WorkerUpdateDto workerCreateDto);
}
