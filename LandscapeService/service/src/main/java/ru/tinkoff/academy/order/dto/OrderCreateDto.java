package ru.tinkoff.academy.order.dto;

import lombok.Data;
import ru.tinkoff.academy.work.WorkEnum;

@Data
public class OrderCreateDto {
    private Long gardenId;
    private Long userId;
    private WorkEnum[] works;
}
