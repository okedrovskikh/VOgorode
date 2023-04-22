package ru.tinkoff.academy.statistics.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.account.AccountService;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class AccountStatisticsService {
    private final AccountService accountService;

    public Timestamp getEarliestCreationDate() {
        return accountService.findEarliestCreationDate();
    }

    public Timestamp getLatestCreationDate() {
        return accountService.findLatestCreationDate();
    }
}
