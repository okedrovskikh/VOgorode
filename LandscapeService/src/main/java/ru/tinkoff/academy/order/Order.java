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
import ru.tinkoff.academy.work.WorkEnum;
import ru.tinkoff.academy.work.WorkEnumType;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "order")
@Table(schema = "public", catalog = "vogorode")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "garden_id", nullable = false)
    private Long gardenId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "works", nullable = false)
    @Enumerated(EnumType.STRING)
    @Type(value = WorkEnumType.class)
    private WorkEnum[] works;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Type(value = OrderStatusEnumType.class)
    private OrderStatus status;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @PrePersist
    public void setCreatedAt() {
        createdAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
