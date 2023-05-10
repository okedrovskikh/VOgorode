package ru.tinkoff.academy.migrations;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.tinkoff.academy.bank.account.BankAccount;

@ChangeUnit(id = "add_bank_account_validator", order = "2", author = "o.kedrovskikh")
public class BankAccountMigration {
    @Execution
    public void addValidator(MongoTemplate mongoTemplate) {
        //mongoTemplate.createCollection(BankAccount.class);
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection(BankAccount.class);
    }
}
