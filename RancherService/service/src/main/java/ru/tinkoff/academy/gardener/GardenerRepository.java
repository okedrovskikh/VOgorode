package ru.tinkoff.academy.gardener;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GardenerRepository extends MongoRepository<Gardener, String> {
}
