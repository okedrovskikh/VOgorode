package ru.tinkoff.academy.field;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateXY;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import ru.tinkoff.academy.field.dto.FieldCreateDto;
import ru.tinkoff.academy.field.dto.FieldDto;
import ru.tinkoff.academy.field.dto.FieldUpdateDto;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FieldMapper {
    @Autowired
    private GeometryFactory geometryFactory;


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gardener", ignore = true)
    @Mapping(target = "area", expression = "java(castToPolygon(createDto.getArea()))")
    public abstract Field dtoToField(FieldCreateDto createDto);

    public Field updateField(Field field, FieldUpdateDto updateDto) {
        field.setAddress(updateDto.getAddress());
        field.setLatitude(updateDto.getLatitude());
        field.setLongitude(updateDto.getLongitude());
        field.setArea(castToPolygon(updateDto.getArea()));
        return field;
    }

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

    @Mapping(target = "area", expression = "java(getAreaSquare(field))")
    public abstract FieldDto toDto(Field field);

    public double getAreaSquare(Field field) {
        Coordinate[] coordinates = field.getArea().getPoints().stream()
                .map(this::toCoordinate)
                .toArray(Coordinate[]::new);
        LinearRing shell = new LinearRing(new CoordinateArraySequence(coordinates), geometryFactory);

        return new org.locationtech.jts.geom.Polygon(shell, null, geometryFactory).getArea();
    }

    private Coordinate toCoordinate(Point point) {
        return new CoordinateXY(point.getX(), point.getY());
    }
}
