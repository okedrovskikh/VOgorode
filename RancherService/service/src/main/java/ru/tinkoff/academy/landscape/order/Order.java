package ru.tinkoff.academy.landscape.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.academy.landscape.order.status.OrderStatus;
import ru.tinkoff.academy.work.WorkEnum;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private Long id;
    private UUID gardenId;
    private UUID workerId;
    private List<WorkEnum> works;
    private OrderStatus status;
    private Timestamp createdAt;
}
