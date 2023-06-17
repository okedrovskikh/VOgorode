package ru.tinkoff.academy.job;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.exceptions.EntityNotFoundException;
import ru.tinkoff.academy.proto.worker.WorkerJobRequest;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobRequestService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    public Job save(WorkerJobRequest request) {
        Job job = jobRepository.findByOrderIdAndStatus(request.getOrderId(), JobStatus.created)
                .orElseGet(() -> jobMapper.mapFromWorkerJobRequest(request));
        return jobRepository.save(job);
    }

    public Job getById(String id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Job request wasn't find by id: %s", id)));
    }

    public Optional<Job> findById(String id) {
        return jobRepository.findByIdAndStatus(id, JobStatus.created);
    }

    public List<Job> findAllByWorkerId(String workerId) {
        return jobRepository.findByWorkersIdContainsAndStatus(List.of(workerId), JobStatus.created);
    }

    public Job deleteWorker(String jobRequestId, String workerId) {
        Job job = getById(jobRequestId);
        job.getWorkersId().remove(workerId);

        if (job.getWorkersId().isEmpty()) {
            job.setStatus(JobStatus.rejected);
            job = jobRepository.save(job);
        } else {
            job = jobRepository.save(job);
        }

        return job;
    }

    public void delete(String orderId) {
        jobRepository.deleteById(orderId);
    }
}
