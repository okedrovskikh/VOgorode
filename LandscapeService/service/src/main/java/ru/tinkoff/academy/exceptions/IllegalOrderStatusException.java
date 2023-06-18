package ru.tinkoff.academy.exceptions;

public class IllegalOrderStatusException extends RuntimeException {
    public IllegalOrderStatusException(String message) {
        super(message);
    }
}
