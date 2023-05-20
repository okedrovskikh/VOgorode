package ru.tinkoff.academy.garden;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GardenRepository extends MongoRepository<Garden, String> {
}
