package ru.tinkoff.academy.handyman.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.proto.worker.WorkerResponse;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class WorkerService {
    private final WorkerGrpcClient workerGrpcClient;
    private final WorkerMapper workerMapper;

    public Future<List<Worker>> formWorkers() {
        Iterator<WorkerResponse> workerResponsesIter = workerGrpcClient.findAll();
        Iterable<WorkerResponse> workerResponses = () -> workerResponsesIter;
        return CompletableFuture.completedFuture(StreamSupport.stream(workerResponses.spliterator(), false)
                .map(workerMapper::mapFromGrpcResponse)
                .toList());
    }
}
