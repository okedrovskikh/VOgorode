package ru.tinkoff.academy.field;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.academy.field.dto.FieldCreateDto;
import ru.tinkoff.academy.field.dto.FieldDto;
import ru.tinkoff.academy.field.dto.FieldUpdateDto;
import ru.tinkoff.academy.field.point.PointMapper;

@Mapper(componentModel = "spring")
public abstract class FieldMapper {
    @Autowired
    protected PointMapper pointMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fielder", ignore = true)
    @Mapping(target = "area", expression = "java(pointMapper.postgisFromPoint(createDto.getArea()))")
    public abstract Field dtoToField(FieldCreateDto createDto);

    public Field updateField(Field field, FieldUpdateDto updateDto) {
        field.setAddress(updateDto.getAddress());
        field.setLatitude(updateDto.getLatitude());
        field.setLongitude(updateDto.getLongitude());
        field.setArea(pointMapper.postgisFromPoint(updateDto.getArea()));
        return field;
    }

    @Mapping(target = "area", expression = "java(pointMapper.pointFromPostgis(field.getArea()))")
    public abstract FieldDto toDto(Field field);
}
