package ru.tinkoff.academy.fielder;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.locationtech.jts.geom.Point;
import ru.tinkoff.academy.field.Field;
import ru.tinkoff.academy.proto.rancher.fielder.FieldResponse;
import ru.tinkoff.academy.proto.rancher.fielder.FielderRequest;
import ru.tinkoff.academy.proto.rancher.fielder.FielderResponse;
import ru.tinkoff.academy.proto.rancher.fielder.FielderServiceGrpc;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class FielderGrpcServiceImpl extends FielderServiceGrpc.FielderServiceImplBase {
    private final FielderService fielderService;

    @Override
    public void getByEmailOrTelephone(FielderRequest request, StreamObserver<FielderResponse> responseObserver) {
        Fielder fielder = fielderService.getByEmailOrTelephone(request.getEmail(), request.getTelephone());
        responseObserver.onNext(mapFielderToResponse(fielder));
        responseObserver.onCompleted();
    }

    private FielderResponse mapFielderToResponse(Fielder fielder) {
        return FielderResponse.newBuilder()
                .setId(fielder.getId())
                .setName(fielder.getName())
                .setSurname(fielder.getSurname())
                .setEmail(fielder.getEmail())
                .setTelephone(fielder.getTelephone())
                .addAllFields(mapFieldsToResponse(fielder.getFields()))
                .build();
    }

    private List<FieldResponse> mapFieldsToResponse(List<Field> fields) {
        return fields.stream().map(this::mapFieldToResponse).toList();
    }

    private FieldResponse mapFieldToResponse(Field field) {
        return FieldResponse.newBuilder()
                .setId(field.getId())
                .setAddress(field.getAddress())
                .setLatitude(field.getLatitude())
                .setLongitude(field.getLongitude())
                .setArea(mapPostgisToPoint(field.getArea()))
                .build();
    }

    private FieldResponse.Point mapPostgisToPoint(Point point) {
        return FieldResponse.Point.newBuilder()
                .setX(point.getX())
                .setY(point.getY())
                .build();
    }
}
