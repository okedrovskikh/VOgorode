package ru.tinkoff.academy.site.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SiteCreateDto {
    private Double latitude;
    private Double longitude;
}
