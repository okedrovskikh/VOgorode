package ru.tinkoff.academy.advice;

import com.google.protobuf.Any;
import com.google.rpc.Code;
import com.google.rpc.ErrorInfo;
import com.google.rpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

import java.util.Arrays;

@net.devh.boot.grpc.server.advice.GrpcAdvice
public class GrpcAdvice {
    @GrpcExceptionHandler(Throwable.class)
    public StatusRuntimeException handleException(Throwable t) {
        String[] stackTrace = Arrays.stream(t.getStackTrace())
                .map(StackTraceElement::toString)
                .toArray(String[]::new);
        ErrorInfo errorInfo = ErrorInfo.newBuilder()
                .setReason(String.join("\n", stackTrace))
                .build();
        Status status = Status.newBuilder()
                .setCode(Code.INTERNAL_VALUE)
                .setMessage(t.getMessage())
                .addDetails(Any.pack(errorInfo))
                .build();
        return StatusProto.toStatusRuntimeException(status);
    }
}
