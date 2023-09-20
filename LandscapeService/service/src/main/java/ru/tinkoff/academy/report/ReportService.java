package ru.tinkoff.academy.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.handyman.worker.grpc.WorkerService;
import ru.tinkoff.academy.rancher.garden.report.GardenReportService;
import ru.tinkoff.academy.handyman.worker.grpc.Worker;
import ru.tinkoff.academy.rancher.garden.report.GardenReport;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final GardenReportService gardenReportService;
    private final WorkerService workerService;
    private final ReportMatcher reportMatcher;

    public Report formReport() throws ExecutionException, InterruptedException {
        Future<List<GardenReport>> gardenReportsFuture = gardenReportService.formGardenReportList();
        Future<List<Worker>> workersFuture = workerService.formWorkers();

        List<GardenReport> gardenReports = gardenReportsFuture.get();
        List<Worker> workers = workersFuture.get();
        boolean isDeficit = reportMatcher.isDeficit(workers, gardenReports);

        return Report.builder()
                .gardenReports(gardenReports)
                .workers(workers)
                .isDeficit(isDeficit)
                .build();
    }
}
