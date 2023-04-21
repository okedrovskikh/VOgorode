package ru.tinkoff.academy.fielder;

import com.google.protobuf.StringValue;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
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
    public void getByEmailAndTelephone(FielderRequest request, StreamObserver<FielderResponse> responseObserver) {
        try {
            String telephone = request.hasTelephone() ? request.getTelephone().getValue() : null;
            Fielder fielder = fielderService.getByEmailAndTelephone(request.getEmail(), telephone);
            responseObserver.onNext(mapFielderToResponse(fielder));
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asException());
        } catch (Throwable t) {
            responseObserver.onError(Status.INTERNAL.withDescription(t.getMessage()).asRuntimeException());
        }
    }

    private FielderResponse mapFielderToResponse(Fielder fielder) {
        FielderResponse.Builder responseBuilder = FielderResponse.newBuilder()
                .setId(fielder.getId())
                .setName(fielder.getName())
                .setSurname(fielder.getSurname())
                .setEmail(fielder.getEmail())
                .addAllFields(mapFieldsToResponse(fielder.getFields()));

        if (fielder.getTelephone() != null) {
            responseBuilder.setTelephone(StringValue.of(fielder.getTelephone()));
        }

        return responseBuilder.build();
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
