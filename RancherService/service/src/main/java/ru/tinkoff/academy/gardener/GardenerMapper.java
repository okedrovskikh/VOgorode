package ru.tinkoff.academy.gardener;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.academy.field.FieldMapper;
import ru.tinkoff.academy.field.FieldService;
import ru.tinkoff.academy.gardener.dto.GardenerCreateDto;
import ru.tinkoff.academy.gardener.dto.GardenerDto;
import ru.tinkoff.academy.gardener.dto.GardenerUpdateDto;

import java.util.List;

@Mapper(componentModel = "spring", imports = {List.class})
public abstract class GardenerMapper {
    @Autowired
    protected FieldService fieldService;
    @Autowired
    protected FieldMapper fieldMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fields", expression = "java(createDto.getFieldsId() == null ? List.of() : fieldService.findAllByIds(createDto.getFieldsId()))")
    public abstract Gardener dtoToFielder(GardenerCreateDto createDto);

    public Gardener updateFielder(Gardener gardener, GardenerUpdateDto updateDto) {
        gardener.setName(updateDto.getName());
        gardener.setSurname(updateDto.getSurname());
        gardener.setEmail(updateDto.getEmail());
        gardener.setTelephone(updateDto.getTelephone());
        gardener.setFields(updateDto.getFieldsId() == null ? List.of() : fieldService.findAllByIds(updateDto.getFieldsId()));
        return gardener;
    }

    @Mapping(target = "fields", expression = "java(gardener.getFields().stream().map(fieldMapper::toDto).toList())")
    public abstract GardenerDto toDto(Gardener gardener);
}
