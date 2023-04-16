package ru.tinkoff.academy.field;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.field.dto.FieldCreateDto;
import ru.tinkoff.academy.field.dto.FieldUpdateDto;

@Mapper(componentModel = "spring")
public abstract class FieldMapper {
    @Mapping(target = "id", ignore = true)
    public abstract Field dtoToField(FieldCreateDto createDto);

    public Field updateField(Field field, FieldUpdateDto updateDto) {
        field.setAddress(updateDto.getAddress());
        field.setLatitude(updateDto.getLatitude());
        field.setLongitude(updateDto.getLongitude());
        field.setArea(updateDto.getArea());
        return field;
    }
}
