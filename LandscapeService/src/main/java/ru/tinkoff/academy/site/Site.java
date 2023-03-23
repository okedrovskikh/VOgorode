package ru.tinkoff.academy.site;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Site {
    private Long id;
    private Double latitude;
    private Double longitude;
}
