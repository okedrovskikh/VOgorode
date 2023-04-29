package ru.tinkoff.academy.statistics.handyman.bank.account;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stat/handyman")
@RequiredArgsConstructor
@Timed(
        value = "stat.request.duration",
        extraTags = {"stat", "bank"},
        description = "Duration Banks stat handling",
        histogram = true
)
public class BankAccountStatisticsController {
    private final BankAccountStatisticsService bankAccountStatisticsService;

    @GetMapping("/banks/all")
    public List<String> findAllBanks() {
        return bankAccountStatisticsService.findAllBanks();
    }
}
