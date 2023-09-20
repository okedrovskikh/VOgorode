package ru.tinkoff.academy.report;

import org.springframework.stereotype.Service;
import ru.tinkoff.academy.handyman.worker.Worker;
import ru.tinkoff.academy.rancher.garden.report.GardenReport;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReportMatcher {
    public boolean isDeficit(List<Worker> workers, List<GardenReport> gardenReports) {
        Map<Set<WorkEnum>, Integer> servicesCount =  workers.stream()
                .map(Worker::getServices)
                .map(Set::copyOf)
                .collect(Collectors.toMap(k -> k, v -> 1, Integer::sum));
        Map<Set<WorkEnum>, Integer> worksCount = gardenReports.stream()
                .map(GardenReport::getWorks)
                .map(Set::copyOf)
                .collect(Collectors.toMap(k -> k, v -> 1, Integer::sum));

        boolean isDeficit = false;

        for (Entry<Set<WorkEnum>, Integer> entry : worksCount.entrySet()) {

            if (entry.getValue() > servicesCount.getOrDefault(entry.getKey(), 0)) {
                isDeficit = true;
                break;
            }

        }

        return isDeficit;
    }
}
