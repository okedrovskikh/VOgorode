package ru.tinkoff.academy.worker;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkerRepository extends MongoRepository<Worker, UUID> {
    @Query("{ '_id' : ?0 }")
    @Update(pipeline = "{ '$set' : ?1 }")
    int updateById(UUID id, Worker garden);
}
