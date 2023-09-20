package ru.tinkoff.academy.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.order.dto.OrderCreateDto;
import ru.tinkoff.academy.order.dto.OrderUpdateDto;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workerId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract Order dtoToOrder(OrderCreateDto createDto);

    public Order updateOrder(Order order, OrderUpdateDto updateDto) {
        order.setWorks(updateDto.getWorks());
        return order;
    }
}
