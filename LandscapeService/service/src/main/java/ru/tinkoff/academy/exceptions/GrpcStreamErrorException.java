package ru.tinkoff.academy.exceptions;

import io.grpc.Status;
import lombok.Getter;

public class GrpcStreamErrorException extends RuntimeException {
    @Getter
    private final Status status;

    public GrpcStreamErrorException(Status status, String message) {
        super(message);
        this.status = status;
    }
}
