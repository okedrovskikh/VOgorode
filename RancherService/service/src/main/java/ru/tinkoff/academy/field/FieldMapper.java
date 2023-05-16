package ru.tinkoff.academy.field;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import ru.tinkoff.academy.field.dto.FieldCreateDto;
import ru.tinkoff.academy.field.dto.FieldDto;
import ru.tinkoff.academy.field.dto.FieldUpdateDto;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FieldMapper {

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

    protected Polygon castToPolygon(List<Double> pointsCoords) {
        if (pointsCoords.size() % 2 != 0) {
            throw new IllegalArgumentException("Area size % 2 cannot be not 0");
        }

        List<Point> points = new ArrayList<>();

        for (int i = 0; i < pointsCoords.size(); i += 2) {
            points.add(new Point(pointsCoords.get(i), pointsCoords.get(i + 1)));
        }

        return new Polygon(points);
    }
}
