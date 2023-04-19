package ru.tinkoff.academy.statistics.handyman;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/stat/handyman")
@RequiredArgsConstructor
public class HandymanStatisticsController {
    private final HandymanStatisticsService handymanService;

    @GetMapping("/banks/all")
    public List<String> findAllBanks() {
        return handymanService.findAllBanks();
    }

    @GetMapping("/date/creation/earliest")
    public LocalDateTime getEarliestCreationDate() {
        return handymanService.getEarliestCreationDate();
    }

    @GetMapping("/date/creation/latest")
    public LocalDateTime getLatestCreationDate() {
        return handymanService.getLatestCreationDate();
    }
}
