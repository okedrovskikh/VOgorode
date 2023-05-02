package ru.tinkoff.academy.handyman.worker;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.academy.proto.worker.WorkerResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.StreamSupport;

@Mapper(componentModel = "spring")
public class AsyncWorkerMapper {
    @Autowired
    private WorkerMapper workerMapper;

    public Future<List<Worker>> formWorkers(Iterable<WorkerResponse> workerResponses) {
        return CompletableFuture.completedFuture(StreamSupport.stream(workerResponses.spliterator(), false)
                .map(workerMapper::mapFromGrpcResponse)
                .toList());
    }
}
