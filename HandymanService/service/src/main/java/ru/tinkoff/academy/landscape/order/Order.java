package ru.tinkoff.academy.landscape.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.academy.landscape.order.status.OrderStatus;
import ru.tinkoff.academy.work.WorkEnum;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private Long id;
    private String gardenId;
    private String workerId;
    private List<WorkEnum> works;
    private OrderStatus orderStatus;
    private Timestamp createdAt;
}
