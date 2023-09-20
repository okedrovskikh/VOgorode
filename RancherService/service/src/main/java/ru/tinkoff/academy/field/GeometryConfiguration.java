package ru.tinkoff.academy.field;

import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class GeometryConfiguration {
    @Bean
    @Primary
    public GeometryFactory geometryFactory() {
        return new GeometryFactory();
    }
}
