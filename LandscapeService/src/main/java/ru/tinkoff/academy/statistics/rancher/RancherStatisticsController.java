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

    @GetMapping("/area/max")
    public Map<String, Double> getUsersMaxArea(@RequestParam(name = "spliter", required = false) Double spliter) {
        return rancherService.getUsersMaxArea(spliter);
    }

    @GetMapping("/area/min")
    public Map<String, Double> getUsersMinArea(@RequestParam(name = "spliter", required = false) Double spliter) {
        return rancherService.getUsersMinArea(spliter);
    }

    @GetMapping("/area/average")
    public Map<String, Double> getUsersAverageArea(@RequestParam(name = "spliter", required = false) Double spliter) {
        return rancherService.getUserAverageArea(spliter);
    }
}
