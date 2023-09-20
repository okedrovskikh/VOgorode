package ru.tinkoff.academy.bank.account;

import java.util.List;

public interface BankAccountCustomRepository {
    List<String> findAllBanks();
}
