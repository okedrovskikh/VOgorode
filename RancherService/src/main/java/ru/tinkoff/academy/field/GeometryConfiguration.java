package ru.tinkoff.academy.field;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTReader;
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

    @Bean
    @Primary
    public WKTReader wktReader(GeometryFactory geometryFactory) {
        return new WKTReader(geometryFactory);
    }
}
