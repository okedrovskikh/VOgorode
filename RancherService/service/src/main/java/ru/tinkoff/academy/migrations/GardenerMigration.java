package ru.tinkoff.academy.migrations;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.tinkoff.academy.gardener.Gardener;

@ChangeUnit(id = "add_gardener_collection_validator", order = "3", author = "o.kedrovskikh")
public class GardenerMigration {
    @Execution
    public void addValidator(MongoTemplate mongoTemplate) {
        mongoTemplate.createCollection(Gardener.class);
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection(Gardener.class);
    }
}
