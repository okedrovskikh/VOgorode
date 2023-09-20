package ru.tinkoff.academy.job;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends MongoRepository<Job, String> {

    Optional<Job> findByIdAndStatus(String id, JobStatus status);

    Optional<Job> findByOrderIdAndStatus(Long orderId, JobStatus status);

    /**
     * Should contain only one id, else wouldn't throw any exception but have incorrect result
     */
    List<Job> findByWorkersIdContainsAndStatus(List<String> workersId, JobStatus status);

    List<Job> findAllByStatus(JobStatus status);
}
