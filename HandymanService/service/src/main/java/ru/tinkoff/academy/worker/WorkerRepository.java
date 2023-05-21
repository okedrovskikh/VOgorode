package ru.tinkoff.academy.worker;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.List;

@Repository
public interface WorkerRepository extends MongoRepository<Worker, String> {
    List<Worker> findAllByServicesContaining(Iterable<WorkEnum> services);
}
