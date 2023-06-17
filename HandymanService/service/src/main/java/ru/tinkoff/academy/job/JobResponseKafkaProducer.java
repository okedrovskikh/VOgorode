package ru.tinkoff.academy.job;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.worker.WorkerJobResponse;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class JobResponseKafkaProducer {
    private final KafkaTemplate<String, WorkerJobResponse> workerJobResponseKafkaTemplate;

    public CompletableFuture<SendResult<String, WorkerJobResponse>> sendEvent(WorkerJobResponse event) {
        return workerJobResponseKafkaTemplate.send("topic", event);
    }
}
