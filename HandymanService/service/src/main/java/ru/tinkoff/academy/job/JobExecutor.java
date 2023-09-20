package ru.tinkoff.academy.job;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.proto.worker.WorkerJobResponse;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JobExecutor {
    private final JobRequestService jobRequestService;
    private final JobService jobService;
    private final JobResponseKafkaProducer jobResponseKafkaProducer;

    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.SECONDS)
    public void sendRejectedResponses() {
        for (Job job : jobRequestService.findAllRejected()) {
            jobResponseKafkaProducer.sendEvent(buildResponse(job.getOrderId(), WorkerJobResponse.WorkerJobEnum.rejected))
                    .whenComplete((res, ex) -> {
                        if (ex == null) {
                            jobRequestService.delete(job.getId());
                        }
                    });
        }
    }

    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.SECONDS)
    public void sendDoneResponses() {
        for (Job job : jobService.findAllDone()) {
            jobResponseKafkaProducer.sendEvent(buildResponse(job.getOrderId(), WorkerJobResponse.WorkerJobEnum.done))
                    .whenComplete((res, ex) -> {
                        if (ex == null) {
                            jobService.delete(job.getId());
                        }
                    });
        }
    }

    private WorkerJobResponse buildResponse(Long orderId, WorkerJobResponse.WorkerJobEnum jobStatus) {
        return WorkerJobResponse.newBuilder()
                .setId(orderId)
                .setStatus(jobStatus)
                .build();
    }
}
