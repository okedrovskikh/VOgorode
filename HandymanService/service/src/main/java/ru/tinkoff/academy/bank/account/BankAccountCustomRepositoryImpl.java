package ru.tinkoff.academy.bank.account;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@RequiredArgsConstructor
public class BankAccountCustomRepositoryImpl implements BankAccountCustomRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public List<String> findAllBanks() {
        return mongoTemplate.findDistinct("bank", BankAccount.class, String.class);
    }
}
