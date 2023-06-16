package ru.tinkoff.academy.handyman.worker.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.proto.worker.WorkerJobResponse;

@Service
@RequiredArgsConstructor
public class WorkerResponseKafkaListener {
    @KafkaListener
    public void listen(@Payload WorkerJobResponse event, Acknowledgment acknowledgment) {
        acknowledgment.acknowledge();
    }
}
