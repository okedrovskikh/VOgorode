package ru.tinkoff.academy.statistics.rancher.area;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AreaStatisticsResponse {
    private Double max;
    private Double average;
    private Double min;
}
