package ru.tinkoff.academy.migrations;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.tinkoff.academy.user.User;

@ChangeUnit(id = "add_user_validation", order = "3", author = "o.kedrovskikh")
public class UserMigration {
    @Execution
    public void addValidator(MongoTemplate mongoTemplate) {
        //mongoTemplate.createCollection(User.class);
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection(User.class);
    }
}
