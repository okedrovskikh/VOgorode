package ru.tinkoff.academy.fielder;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.academy.field.FieldMapper;
import ru.tinkoff.academy.field.FieldService;
import ru.tinkoff.academy.fielder.dto.FielderCreateDto;
import ru.tinkoff.academy.fielder.dto.FielderDto;
import ru.tinkoff.academy.fielder.dto.FielderUpdateDto;

import java.util.List;

@Mapper(componentModel = "spring", imports = {List.class})
public abstract class FielderMapper {
    @Autowired
    protected FieldService fieldService;
    @Autowired
    protected FieldMapper fieldMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fields", expression = "java(createDto.getFieldsId() == null ? List.of() : fieldService.findAllByIds(createDto.getFieldsId()))")
    public abstract Fielder dtoToFielder(FielderCreateDto createDto);

    public Fielder updateFielder(Fielder fielder, FielderUpdateDto updateDto) {
        fielder.setName(updateDto.getName());
        fielder.setSurname(updateDto.getSurname());
        fielder.setEmail(updateDto.getEmail());
        fielder.setTelephone(updateDto.getTelephone());
        fielder.setFields(updateDto.getFieldsId() == null ? List.of() : fieldService.findAllByIds(updateDto.getFieldsId()));
        return fielder;
    }

    @Mapping(target = "fields", expression = "java(fielder.getFields().stream().map(fieldMapper::toDto).toList())")
    public abstract FielderDto toDto(Fielder fielder);
}
