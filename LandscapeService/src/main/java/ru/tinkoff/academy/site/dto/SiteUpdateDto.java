package ru.tinkoff.academy.site.dto;

import lombok.Data;

@Data
public class SiteUpdateDto {
    private Long id;
    private Double latitude;
    private Double longitude;
}
