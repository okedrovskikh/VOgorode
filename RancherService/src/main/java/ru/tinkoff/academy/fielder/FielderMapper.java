package ru.tinkoff.academy.fielder;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.academy.field.FieldService;
import ru.tinkoff.academy.fielder.dto.FielderCreateDto;
import ru.tinkoff.academy.fielder.dto.FielderUpdateDto;

import javax.annotation.PostConstruct;

@Mapper(componentModel = "spring")
public abstract class FielderMapper {
    protected FieldService fieldService;

    @PostConstruct
    public void setFieldService(@Autowired FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fields", expression = "java(fieldService.findAllByIds(createDto.getFieldsId()))")
    public abstract Fielder dtoToFielder(FielderCreateDto createDto);

    public Fielder updateFielder(Fielder fielder, FielderUpdateDto updateDto) {
        fielder.setName(updateDto.getName());
        fielder.setSurname(updateDto.getSurname());
        fielder.setEmail(updateDto.getEmail());
        fielder.setTelephone(updateDto.getTelephone());
        fielder.setFields(fieldService.findAllByIds(updateDto.getFieldsId()));
        return fielder;
    }
}
