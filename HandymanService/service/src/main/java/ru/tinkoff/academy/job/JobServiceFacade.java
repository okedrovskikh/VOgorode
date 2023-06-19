package ru.tinkoff.academy.job;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.exceptions.JobAlreadyAcceptedException;
import ru.tinkoff.academy.landscape.order.Order;
import ru.tinkoff.academy.landscape.order.OrderWebClientHelper;
import ru.tinkoff.academy.landscape.order.dto.WorkerUpdateDto;
import ru.tinkoff.academy.landscape.order.status.OrderStatus;
import ru.tinkoff.academy.worker.Worker;
import ru.tinkoff.academy.worker.WorkerService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceFacade {
    private final JobRequestService jobRequestService;
    private final JobService jobService;
    private final WorkerService workerService;
    private final OrderWebClientHelper orderWebClientHelper;

    public Job acceptJobRequest(String jobRequestId, String workerId) {
        Job jobRequest = jobRequestService.getById(jobRequestId);
        Worker worker = workerService.getById(workerId);

        updateOrder(jobRequest, worker);

        return jobService.save(jobRequest, worker);
    }

    private void updateOrder(Job jobRequest, Worker worker) {
        Order order = orderWebClientHelper.updateOrderWorkerId(jobRequest.getOrderId(), worker.getAccountId()).block();

        if (!worker.getAccountId().equals(order.getWorkerId())) {
            throw new JobAlreadyAcceptedException(String.format("Job request with id: %s already accepted", jobRequest.getId()));
        }
    }

    public void finishJob(String jobId) {
        jobService.updateStatus(jobId, JobStatus.done);
    }

    public List<Job> findAllWorkerRequests(String workerId) {
        return jobRequestService.findAllByWorkerId(workerId);
    }

    public void rejectJobRequest(String workerId, String jobRequestId) {
        jobRequestService.deleteWorker(jobRequestId, workerId);
    }
}
