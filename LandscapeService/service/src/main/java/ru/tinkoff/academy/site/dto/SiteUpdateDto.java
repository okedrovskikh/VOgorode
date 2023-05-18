package ru.tinkoff.academy.site.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SiteUpdateDto {
    private UUID id;
    private String latitude;
    private String longitude;
}
