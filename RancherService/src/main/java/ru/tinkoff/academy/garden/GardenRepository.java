package ru.tinkoff.academy.garden;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface GardenRepository extends MongoRepository<Garden, Long> {
    @Query("{ '_id' : ?1 }")
    @Update("{ '$set' : ?2 }")
    int update(String id, Garden garden);
}
