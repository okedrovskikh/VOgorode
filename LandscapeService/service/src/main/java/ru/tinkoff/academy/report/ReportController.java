package ru.tinkoff.academy.report;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
@Timed(
        value = "business.request.duration",
        extraTags = {"process", "report"},
        description = "Duration Report service handling",
        histogram = true
)
public class ReportController {
    private final ReportService reportService;

    @GetMapping("")
    public Report formReport() throws ExecutionException, InterruptedException {
        return reportService.formReport();
    }
}
