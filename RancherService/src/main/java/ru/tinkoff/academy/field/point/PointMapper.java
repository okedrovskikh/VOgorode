package ru.tinkoff.academy.field.point;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.impl.PackedCoordinateSequence;
import org.springframework.stereotype.Component;

@Component
public class PointMapper {
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public Point postgisFromPoint(ru.tinkoff.academy.field.point.Point requestPoint) {
        final int dimension = 2;
        final int measure = 0;
        var coordinate = new PackedCoordinateSequence.Double(new double[]{requestPoint.getX(), requestPoint.getY()}, dimension, measure);
        return new Point(coordinate, geometryFactory);
    }

    public ru.tinkoff.academy.field.point.Point pointFromPostgis(Point point) {
        return new ru.tinkoff.academy.field.point.Point(point.getX(), point.getY());
    }
}
