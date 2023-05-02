package ru.tinkoff.academy.report;

import lombok.Data;
import ru.tinkoff.academy.proto.gardener.Gardener;

import java.util.List;

@Data
public class Report {
    private List<GardenReport> gardenReports;
    private List<Gardener> gardeners;
    private boolean isDeficit;
}
