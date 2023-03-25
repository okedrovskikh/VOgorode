package ru.tinkoff.academy.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler({EmptyResultDataAccessException.class, DataIntegrityViolationException.class,
            IllegalArgumentException.class, NumberFormatException.class, NullPointerException.class})
    public ResponseEntity<?> handleIllegalIdException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
