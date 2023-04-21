package ru.tinkoff.academy.statistics.rancher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.tinkoff.academy.statistics.rancher.RancherStatisticsService.FieldResponseComparator;
import ru.tinkoff.academy.statistics.rancher.RancherStatisticsService.PointComparator;

@Configuration
public class RancherComparatorsConfiguration {
    @Bean
    @Primary
    public FieldResponseComparator fieldResponseComparator(PointComparator pointComparator) {
        return new FieldResponseComparator(pointComparator);
    }

    @Bean
    @Primary
    public PointComparator pointComparator() {
        return new PointComparator();
    }
}
