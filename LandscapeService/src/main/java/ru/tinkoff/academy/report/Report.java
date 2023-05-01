package ru.tinkoff.academy.report;

import lombok.Data;
import ru.tinkoff.academy.proto.gardener.Gardener;

import java.util.List;

@Data
public class Report {
    private GardenReport gardenReport;
    private List<Gardener> gardeners;
    private boolean isDeficit;
}
