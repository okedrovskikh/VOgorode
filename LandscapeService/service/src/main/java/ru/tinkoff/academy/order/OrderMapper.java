package ru.tinkoff.academy.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.order.dto.OrderCreateDto;
import ru.tinkoff.academy.order.dto.OrderUpdateDto;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract Order dtoToOrder(OrderCreateDto createDto);

    public Order updateOrder(Order order, OrderUpdateDto updateDto) {
        order.setGardenId(updateDto.getGardenId());
        order.setUserId(updateDto.getUserId());
        order.setWorks(updateDto.getWorks());
        order.setStatus(updateDto.getStatus());
        return order;
    }
}
