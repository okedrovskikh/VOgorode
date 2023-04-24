package ru.tinkoff.academy.field;

import com.google.rpc.Code;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.tinkoff.academy.proto.field.FieldServiceGrpc;
import ru.tinkoff.academy.proto.field.SplitValueRequest;
import ru.tinkoff.academy.proto.field.SplitValueResponse;
import ru.tinkoff.academy.proto.field.SplitValueResponseQuote;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class FieldGrpcServiceImpl extends FieldServiceGrpc.FieldServiceImplBase {
    private static final String splitRange = "%s - %s";

    private final FieldService fieldService;

    @Override
    public void getAreasStatBySplitValue(SplitValueRequest request, StreamObserver<SplitValueResponseQuote> responseObserver) {
        List<Object[]> stats;
        try {
            stats = fieldService.findAreaStatBySplitValue(request.getSplitValue()).stream()
                    .map(s -> (Object[]) s)
                    .toList();
        } catch (Throwable t) {
            responseObserver.onNext(SplitValueResponseQuote.newBuilder()
                    .setError(com.google.rpc.Status.newBuilder().setCode(Code.INTERNAL.getNumber())
                            .setMessage(t.getMessage()).build()).build());
            responseObserver.onCompleted();
            return;
        }

        for (Object[] stat : stats) {
            try {
                responseObserver.onNext(SplitValueResponseQuote.newBuilder()
                        .setResponse(SplitValueResponse.newBuilder()
                                .setSplitValueRange(formSplitRange(((Double) stat[0]).longValue(), (long) request.getSplitValue()))
                                .setMax((Double) stat[1])
                                .setAverage((Double) stat[2])
                                .setMin((Double) stat[3])
                                .build()
                        ).build()
                );
            } catch (Throwable t) {
                responseObserver.onNext(SplitValueResponseQuote.newBuilder()
                        .setError(com.google.rpc.Status.newBuilder().setCode(Code.INTERNAL.getNumber())
                                .setMessage(t.getMessage()).build()).build());
            }
        }

        responseObserver.onCompleted();
    }

    private String formSplitRange(long splitBucket, long multilayer) {
        return String.format(splitRange, splitBucket * multilayer, (splitBucket + 1) * multilayer);
    }
}
