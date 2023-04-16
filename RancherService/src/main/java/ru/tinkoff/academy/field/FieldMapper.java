package ru.tinkoff.academy.field;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.academy.field.dto.FieldCreateDto;
import ru.tinkoff.academy.field.dto.FieldUpdateDto;
import ru.tinkoff.academy.fielder.FielderService;

import javax.annotation.PostConstruct;

@Mapper(componentModel = "spring")
public abstract class FieldMapper {
    protected FielderService fielderService;

    @PostConstruct
    public void setFielderService(@Autowired FielderService fielderService) {
        this.fielderService = fielderService;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fielder", expression = "java(fielderService.getById(createDto.getFielderId()))")
    public abstract Field dtoToField(FieldCreateDto createDto);

    public Field updateField(Field field, FieldUpdateDto updateDto) {
        field.setAddress(updateDto.getAddress());
        field.setLatitude(updateDto.getLatitude());
        field.setLongitude(updateDto.getLongitude());
        field.setArea(updateDto.getArea());
        field.setFielder(fielderService.getById(updateDto.getFielderId()));
        return field;
    }
}
