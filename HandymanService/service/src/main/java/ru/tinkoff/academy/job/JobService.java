package ru.tinkoff.academy.job;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.exceptions.EntityNotFoundException;
import ru.tinkoff.academy.worker.Worker;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;


    public Job save(Job jobRequest, Worker worker) {
        jobRequest.setStatus(JobStatus.accepted);
        jobRequest.setWorkersId(List.of(worker.getId()));
        return jobRepository.save(jobRequest);
    }

    public Job getById(String id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Job wasn't find by id: %s", id)));
    }

    public Optional<Job> findById(String id) {
        return jobRepository.findByIdAndStatus(id, JobStatus.accepted);
    }

    public Job updateStatus(String jobId, JobStatus status) {
        Job job = getById(jobId);
        job.setStatus(status);
        return jobRepository.save(job);
    }
}
