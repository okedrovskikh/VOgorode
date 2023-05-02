package ru.tinkoff.academy.report;

import lombok.Data;
import ru.tinkoff.academy.work.WorkEnum;

@Data
public class GardenReport {
    private WorkEnum work;
    private int amount;
}
