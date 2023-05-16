package ru.tinkoff.academy.fielder;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FielderRepository extends MongoRepository<Fielder, String> {
}
