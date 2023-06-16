package ru.tinkoff.academy.report;

import lombok.Builder;
import lombok.Data;
import ru.tinkoff.academy.handyman.worker.grpc.Worker;
import ru.tinkoff.academy.rancher.garden.report.GardenReport;

import java.util.List;

@Data
@Builder
public class Report {
    private List<GardenReport> gardenReports;
    private List<Worker> workers;
    private boolean isDeficit;
}
