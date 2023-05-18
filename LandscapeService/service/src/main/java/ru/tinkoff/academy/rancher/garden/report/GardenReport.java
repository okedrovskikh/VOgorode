package ru.tinkoff.academy.rancher.garden.report;

import lombok.Data;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.List;

@Data
public class GardenReport {
    private List<WorkEnum> works;
}
