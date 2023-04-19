package ru.tinkoff.academy.statistics.handyman;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.account.Account;
import ru.tinkoff.academy.account.AccountService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HandymanStatisticsService {
    private final AccountService accountService;

    public List<String> findAllBanks() {
        List<Account> accounts = accountService.findAll();
        // todo go to handyman, get handyman users, get accounts
        // todo get accounts banks
        return List.of();
    }

    public LocalDateTime getEarliestCreationDate() {
        return null;
    }

    public LocalDateTime getLatestCreationDate() {
        return null;
    }
}
