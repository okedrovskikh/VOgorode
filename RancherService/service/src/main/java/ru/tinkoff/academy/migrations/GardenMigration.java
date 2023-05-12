package ru.tinkoff.academy.migrations;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.tinkoff.academy.garden.Garden;

@ChangeUnit(id = "add_garden_collection_validator", order = "1", author = "o.kedrovskikh")
public class GardenMigration {
    @Execution
    public void addValidator(MongoTemplate mongoTemplate) {
        mongoTemplate.createCollection(Garden.class);
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection(Garden.class);
    }
}
