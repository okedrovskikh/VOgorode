package ru.tinkoff.academy.field;

import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.academy.field.dto.FieldCreateDto;
import ru.tinkoff.academy.field.dto.FieldDto;
import ru.tinkoff.academy.field.dto.FieldUpdateDto;
import ru.tinkoff.academy.field.point.PointMapper;
import ru.tinkoff.academy.fielder.FielderService;

@Mapper(componentModel = "spring")
public abstract class FieldMapper {
    @Setter
    protected FielderService fielderService;
    @Autowired
    protected PointMapper pointMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fielder", expression = "java(fielderService.getById(createDto.getFielderId()))")
    @Mapping(target = "area", expression = "java(pointMapper.postgisFromPoint(createDto.getArea()))")
    public abstract Field dtoToField(FieldCreateDto createDto);

    public Field updateField(Field field, FieldUpdateDto updateDto) {
        field.setAddress(updateDto.getAddress());
        field.setLatitude(updateDto.getLatitude());
        field.setLongitude(updateDto.getLongitude());
        field.setArea(pointMapper.postgisFromPoint(updateDto.getArea()));
        field.setFielder(fielderService.getById(updateDto.getFielderId()));
        return field;
    }

    @Mapping(target = "area", expression = "java(pointMapper.pointFromPostgis(field.getArea()))")
    public abstract FieldDto toDto(Field field);
}
