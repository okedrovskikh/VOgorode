package ru.tinkoff.academy.statistics.account;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/stat/accounts")
@RequiredArgsConstructor
@Timed(
        value = "stat.request.duration",
        extraTags = {"stat", "account"},
        description = "Duration Accounts stat handling",
        histogram = true
)
public class AccountStatisticsController {
    private final AccountStatisticsService accountStatisticsService;

    @GetMapping("/date/creation/earliest")
    public Timestamp getEarliestCreationDate() {
        return accountStatisticsService.getEarliestCreationDate();
    }

    @GetMapping("/date/creation/latest")
    public Timestamp getLatestCreationDate() {
        return accountStatisticsService.getLatestCreationDate();
    }
}
