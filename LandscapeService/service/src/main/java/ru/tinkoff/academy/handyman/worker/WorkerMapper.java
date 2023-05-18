package ru.tinkoff.academy.handyman.worker;

import org.mapstruct.Mapper;
import ru.tinkoff.academy.proto.worker.WorkerResponse;

@Mapper(componentModel = "spring")
public interface WorkerMapper {
    Worker mapFromGrpcResponse(WorkerResponse response);
}
