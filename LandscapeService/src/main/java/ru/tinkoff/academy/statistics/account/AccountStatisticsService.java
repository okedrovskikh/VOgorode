package ru.tinkoff.academy.statistics.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.account.Account;
import ru.tinkoff.academy.account.AccountService;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class AccountStatisticsService {
    private final AccountService accountService;

    public LocalDateTime getEarliestCreationDate() {
        return accountService.findAll().stream().min(new AccountComparator())
                .orElseThrow(IllegalStateException::new).getCreationDate().toLocalDateTime();
    }

    public LocalDateTime getLatestCreationDate() {
        return accountService.findAll().stream().max(new AccountComparator())
                .orElseThrow(IllegalStateException::new).getCreationDate().toLocalDateTime();
    }

    public static class AccountComparator implements Comparator<Account> {

        @Override
        public int compare(Account o1, Account o2) {
            return o1.getCreationDate().toLocalDateTime()
                    .compareTo(o2.getCreationDate().toLocalDateTime());
        }
    }
}
