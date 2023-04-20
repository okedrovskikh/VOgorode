package ru.tinkoff.academy.statistics.account;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/stat/accounts")
@RequiredArgsConstructor
public class AccountStatisticsController {
    private final AccountStatisticsService accountStatisticsService;

    @GetMapping("/date/creation/earliest")
    public LocalDateTime getEarliestCreationDate() {
        return accountStatisticsService.getEarliestCreationDate();
    }

    @GetMapping("/date/creation/latest")
    public LocalDateTime getLatestCreationDate() {
        return accountStatisticsService.getLatestCreationDate();
    }
}
