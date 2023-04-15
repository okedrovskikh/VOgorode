package ru.tinkoff.academy.gardener;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.gardener.dto.GardenerCreateDto;
import ru.tinkoff.academy.gardener.dto.GardenerUpdateDto;

@Mapper(componentModel = "spring")
public abstract class GardenerMapper {
    @Mapping(target = "id", ignore = true)
    public abstract Gardener dtoToGardener(GardenerCreateDto createDto);

    public Gardener updateGardener(Gardener gardener, GardenerUpdateDto updateDto) {
        gardener.setName(updateDto.getName());
        gardener.setSurname(updateDto.getSurname());
        gardener.setTelephone(updateDto.getTelephone());
        return gardener;
    }
}
