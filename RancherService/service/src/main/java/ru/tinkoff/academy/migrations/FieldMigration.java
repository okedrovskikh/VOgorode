package ru.tinkoff.academy.migrations;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.tinkoff.academy.field.Field;

@ChangeUnit(id = "add_field_collection_validator", order = "2", author = "o.kedrovskikh")
public class FieldMigration {
    @Execution
    public void addValidator(MongoTemplate mongoTemplate) {
        mongoTemplate.createCollection(Field.class);
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection(Field.class);
    }
}
