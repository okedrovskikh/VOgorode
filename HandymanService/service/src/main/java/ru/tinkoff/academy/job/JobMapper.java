package ru.tinkoff.academy.job;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.proto.worker.WorkerJobRequest;

import java.util.List;

@Mapper(componentModel = "spring", imports = {List.class})
public interface JobMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(JobStatus.created)")
    @Mapping(target = "orderId", expression = "java(workerJobRequest.getOrderId())")
    @Mapping(target = "workersId", expression = "java(List.of(workerJobRequest.getId()))")
    Job mapFromWorkerJobRequest(WorkerJobRequest workerJobRequest);
}
