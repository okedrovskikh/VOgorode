package ru.tinkoff.academy.job;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.proto.worker.WorkerJobRequest;

@Mapper(componentModel = "spring")
public interface JobMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(JobStatus.created)")
    @Mapping(target = "orderId", expression = "java(workerJobRequest.getOrderId())")
    @Mapping(target = "workersId", expression = "java(List.of(workerJobRequest.getId()))")
    Job mapFromWorkerJobRequest(WorkerJobRequest workerJobRequest);
}
