package ru.tinkoff.academy.exceptions;

public class OrderIncompleteException extends RuntimeException {
    public OrderIncompleteException(String message) {
        super(message);
    }
}
