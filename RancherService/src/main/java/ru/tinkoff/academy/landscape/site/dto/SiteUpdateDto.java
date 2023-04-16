package ru.tinkoff.academy.landscape.site.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SiteUpdateDto {
    private UUID id;
    private Double latitude;
    private Double longitude;
}
