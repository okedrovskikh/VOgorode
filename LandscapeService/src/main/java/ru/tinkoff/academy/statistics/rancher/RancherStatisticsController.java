package ru.tinkoff.academy.statistics.rancher;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/stat/rancher")
@RequiredArgsConstructor
public class RancherStatisticsController {
    private final RancherStatisticsService rancherService;

    @GetMapping("/area")
    public Map<String, AreaStatisticsResponse> getAreasStatSplit(
            @RequestParam(name = "splitValue", required = false, defaultValue = "0.0") Double splitValue) {
        return rancherService.getAreasStatSplit(splitValue);
    }
}
