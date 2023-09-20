package ru.tinkoff.academy.exceptions;

public class OrderCompletedException extends RuntimeException {
    public OrderCompletedException(String message) {
        super(message);
    }
}
