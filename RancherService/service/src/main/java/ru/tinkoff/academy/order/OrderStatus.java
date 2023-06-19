package ru.tinkoff.academy.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "order")
public class OrderStatus {
    @Id
    private String id;
    @Field(name = "garden_id")
    private String gardenId;
    @Field(name = "status")
    private OrderCreationStatus status;
    @Field(name = "order_id")
    private Long orderId;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OrderStatus orderStatus)) {
            return false;
        }

        return Objects.equals(id, orderStatus.id);
    }
}
