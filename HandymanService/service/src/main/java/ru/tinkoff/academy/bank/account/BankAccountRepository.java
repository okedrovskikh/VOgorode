package ru.tinkoff.academy.bank.account;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends MongoRepository<BankAccount, String>, BankAccountCustomRepository {
}
