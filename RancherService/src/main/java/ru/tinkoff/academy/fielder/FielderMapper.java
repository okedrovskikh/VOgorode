package ru.tinkoff.academy.fielder;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.fielder.dto.FielderCreateDto;
import ru.tinkoff.academy.fielder.dto.FielderUpdateDto;

@Mapper(componentModel = "spring")
public abstract class FielderMapper {
    @Mapping(target = "id", ignore = true)
    public abstract Fielder dtoToGardener(FielderCreateDto createDto);

    public Fielder updateGardener(Fielder fielder, FielderUpdateDto updateDto) {
        fielder.setName(updateDto.getName());
        fielder.setSurname(updateDto.getSurname());
        fielder.setEmail(updateDto.getEmail());
        fielder.setTelephone(updateDto.getTelephone());
        return fielder;
    }
}
