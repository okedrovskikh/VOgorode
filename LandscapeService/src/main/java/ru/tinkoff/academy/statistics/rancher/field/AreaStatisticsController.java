package ru.tinkoff.academy.statistics.rancher.field;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/stat/rancher")
@RequiredArgsConstructor
@Timed(
        value = "stat.request.duration",
        extraTags = {"stat", "area"},
        description = "Duration Areas stat handling",
        histogram = true
)
public class AreaStatisticsController {
    private final AreaStatisticsService areaStatisticsService   ;

    @GetMapping("/area")
    public Map<String, AreaStatisticsResponse> getAreasStatSplit(
            @RequestParam(name = "splitValue", required = false, defaultValue = "0.0") Double splitValue) {
        return areaStatisticsService.getAreasStatSplit(splitValue);
    }
}
