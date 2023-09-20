package ru.tinkoff.academy.handyman.worker;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.academy.proto.worker.WorkerResponse;
import ru.tinkoff.academy.work.WorkEnumMapper;

@Mapper(componentModel = "spring")
public abstract class WorkerMapper {
    @Autowired
    protected WorkEnumMapper workEnumMapper;

    @Mapping(target = "services", expression = "java(response.getServicesList().stream().map(workEnumMapper::fromGrpcEnum).toList())")
    public abstract Worker mapFromGrpcResponse(WorkerResponse response);
}
