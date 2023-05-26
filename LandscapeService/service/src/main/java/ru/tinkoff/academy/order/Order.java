package ru.tinkoff.academy.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import ru.tinkoff.academy.order.status.OrderStatus;
import ru.tinkoff.academy.order.status.OrderStatusEnumType;
import ru.tinkoff.academy.work.WorkEnum;
import ru.tinkoff.academy.work.WorkEnumArrayType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "l_order")
@Table(schema = "public", catalog = "vogorode")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "garden_id", nullable = false)
    private String gardenId;
    @Column(name = "worker_id")
    private String workerId;
    @Column(name = "works", nullable = false)
    @Type(value = WorkEnumArrayType.class)
    private WorkEnum[] works;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Type(value = OrderStatusEnumType.class)
    private OrderStatus status;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = Timestamp.valueOf(LocalDateTime.now());
        status = OrderStatus.created;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Order order)) {
            return false;
        }

        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }
}
