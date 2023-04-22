package ru.tinkoff.academy.fielder;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.tinkoff.academy.proto.rancher.fielder.FielderRequest;
import ru.tinkoff.academy.proto.rancher.fielder.FielderResponse;
import ru.tinkoff.academy.proto.rancher.fielder.FielderServiceGrpc;

@GrpcService
@RequiredArgsConstructor
public class FielderGrpcServiceImpl extends FielderServiceGrpc.FielderServiceImplBase {
    private final FielderService fielderService;

    @Override
    public void getAreasStatByEmail(FielderRequest request, StreamObserver<FielderResponse> responseObserver) {
        try {
            Double[] stat;

            if (request.hasTelephone()) {
                stat = fielderService.getAreasStatByEmailAndTelephone(request.getEmail(), request.getTelephone().getValue());
            } else {
                stat = fielderService.getAreasStatByEmail(request.getEmail());
            }

            if (stat.length == 1) {
                responseObserver.onNext(FielderResponse.newBuilder()
                        .setMax(stat[0])
                        .setAverage(stat[0])
                        .setMin(stat[0])
                        .build()
                );
            } else {
                responseObserver.onNext(FielderResponse.newBuilder()
                        .setMax(stat[0])
                        .setAverage(stat[1])
                        .setMin(stat[2])
                        .build()
                );
            }
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        } catch (Throwable t) {
            responseObserver.onError(Status.INTERNAL.withDescription(t.getMessage()).asRuntimeException());
        }
    }
}
