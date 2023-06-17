package ru.tinkoff.academy.job;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.worker.WorkerJobRequest;

@Component
@RequiredArgsConstructor
public class JobRequestKafkaListener {
    private final JobRequestService jobRequestService;

    @KafkaListener(
            topics = "topic",
            containerFactory = "workerJobRequestListenerContainerFactory"
    )
    public void listen(@Payload WorkerJobRequest event) {
        jobRequestService.save(event);
    }
}
