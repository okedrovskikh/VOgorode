package ru.tinkoff.academy.landscape.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.tinkoff.academy.work.WorkEnum;

@Data
@AllArgsConstructor
@Builder
public class OrderUpdateDto {
    private Long id;
    private WorkEnum[] works;
}
