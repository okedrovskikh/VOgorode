package ru.tinkoff.academy.exceptions;

public class JobAlreadyAcceptedException extends RuntimeException {

    public JobAlreadyAcceptedException(String message) {
        super(message);
    }
}
