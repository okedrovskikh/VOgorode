package ru.tinkoff.academy.handyman.worker.grpc;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.proto.worker.WorkerResponse;
import ru.tinkoff.academy.work.WorkEnum;
import ru.tinkoff.academy.work.WorkEnumMapper;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class WorkerService {
    private final WorkerGrpcClient workerGrpcClient;
    private final WorkerMapper workerMapper;
    private final WorkEnumMapper workEnumMapper;

    @Async
    public Future<List<Worker>> formWorkers() {
        Iterator<WorkerResponse> workerResponsesIter = workerGrpcClient.findAll();
        Iterable<WorkerResponse> workerResponses = () -> workerResponsesIter;
        return CompletableFuture.completedFuture(StreamSupport.stream(workerResponses.spliterator(), false)
                .map(workerMapper::mapFromGrpcResponse)
                .toList());
    }

    public List<Worker> findWorker(Collection<WorkEnum> works, Double latitude, Double longitude) {
        Set<ru.tinkoff.academy.proto.work.WorkEnum> services = works.stream()
                .map(workEnumMapper::toGrpcEnum)
                .collect(Collectors.toSet());
        Iterator<WorkerResponse> workersByServicesIterator = workerGrpcClient.findByServices(services);
        Iterable<WorkerResponse> workerByServicesResponses = () -> workersByServicesIterator;
        List<WorkerResponse> workerResponses = StreamSupport.stream(workerByServicesResponses.spliterator(), false).toList();

        if (workerResponses.isEmpty()) {
            return List.of();
        }

        List<WorkerResponse> topWorkersByDistance = workerResponses.stream()
                .map(w -> Pair.of(w, distance(w.getLatitude(), latitude, w.getLongitude(), longitude)))
                .sorted(Comparator.comparingDouble(Pair::getSecond))
                .limit(3)
                .map(Pair::getFirst)
                .toList();

        return topWorkersByDistance.stream().map(workerMapper::mapFromGrpcResponse).toList();
    }

    private double distance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 1) + Math.pow(y1 - y2, 2));
    }
}
