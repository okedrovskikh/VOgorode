package ru.tinkoff.academy.fielder;

import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.tinkoff.academy.proto.fielder.FielderRequest;
import ru.tinkoff.academy.proto.fielder.FielderResponse;
import ru.tinkoff.academy.proto.fielder.FielderServiceGrpc;

@GrpcService
@RequiredArgsConstructor
public class FielderGrpcServiceImpl extends FielderServiceGrpc.FielderServiceImplBase {
    private final FielderService fielderService;

    @Override
    public void getAreasStatByEmailAndTelephone(FielderRequest request, StreamObserver<FielderResponse> responseObserver) {
        try {
            Object stat;

            if (request.hasTelephone()) {
                stat = fielderService.getAreasStatByEmailAndTelephone(request.getEmail(), request.getTelephone().getValue());
            } else {
                stat = fielderService.getAreasStatByEmail(request.getEmail());
            }

            responseObserver.onNext(FielderResponse.newBuilder()
                    .setMax((Double) ((Object[]) stat)[0])
                    .setAverage((Double) ((Object[]) stat)[1])
                    .setMin((Double) ((Object[]) stat)[2])
                    .build()
            );
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        } catch (Throwable t) {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(t.getMessage()).asRuntimeException());
        }
    }
}
