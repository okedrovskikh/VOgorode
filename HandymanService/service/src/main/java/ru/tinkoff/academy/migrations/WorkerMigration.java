package ru.tinkoff.academy.migrations;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.tinkoff.academy.worker.Worker;

@ChangeUnit(id = "add_worker_validation", order = "1", author = "o.kedrovskikh")
public class WorkerMigration {
    @Execution
    public void addValidator(MongoTemplate mongoTemplate) {
        mongoTemplate.createCollection(Worker.class);
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection(Worker.class);
    }
}
