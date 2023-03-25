package ru.tinkoff.academy.site.dto;

import lombok.Data;

@Data
public class SiteUpdateDto {
    private String id;
    private String latitude;
    private String longitude;
}
