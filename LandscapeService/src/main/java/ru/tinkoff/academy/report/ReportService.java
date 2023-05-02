package ru.tinkoff.academy.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.handyman.worker.AsyncWorkerMapper;
import ru.tinkoff.academy.rancher.garden.report.AsyncGardenReportMapper;
import ru.tinkoff.academy.handyman.worker.Worker;
import ru.tinkoff.academy.handyman.worker.WorkerGrpcClient;
import ru.tinkoff.academy.rancher.garden.report.GardenGrpcClient;
import ru.tinkoff.academy.rancher.garden.report.GardenReport;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final GardenGrpcClient gardenGrpcClient;
    private final AsyncGardenReportMapper asyncGardenReportMapper;
    private final WorkerGrpcClient workerGrpcClient;
    private final AsyncWorkerMapper asyncWorkerMapper;
    private final ReportMatcher reportMatcher;

    public Report formReport() throws ExecutionException, InterruptedException {
        Future<List<GardenReport>> gardenReportsFuture = asyncGardenReportMapper.formGardenReportList(gardenGrpcClient::formReport);
        Future<List<Worker>> workersFuture = asyncWorkerMapper.formWorkers(workerGrpcClient::findAll);

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
