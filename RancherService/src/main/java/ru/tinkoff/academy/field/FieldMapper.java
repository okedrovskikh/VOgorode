package ru.tinkoff.academy.field;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.academy.field.dto.FieldCreateDto;
import ru.tinkoff.academy.field.dto.FieldDto;
import ru.tinkoff.academy.field.dto.FieldUpdateDto;

@Mapper(componentModel = "spring")
public abstract class FieldMapper {
    @Autowired
    private WKTReader wktReader;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fielder", ignore = true)
    @Mapping(target = "area", expression = "java(castToPolygon(createDto.getArea()))")
    public abstract Field dtoToField(FieldCreateDto createDto);

    public Field updateField(Field field, FieldUpdateDto updateDto) {
        field.setAddress(updateDto.getAddress());
        field.setLatitude(updateDto.getLatitude());
        field.setLongitude(updateDto.getLongitude());
        field.setArea(castToPolygon(updateDto.getArea()));
        return field;
    }

    @Mapping(target = "area", expression = "java(field.getArea().getArea())")
    public abstract FieldDto toDto(Field field);

    protected Polygon castToPolygon(String geometryString) {
        Geometry geometry;
        try {
            geometry = wktReader.read(geometryString);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        if (!(geometry instanceof Polygon polygon)) {
            throw new IllegalArgumentException(String.format("%s is not a polygon", geometry.toString()));
        }

        return polygon;
    }
}
