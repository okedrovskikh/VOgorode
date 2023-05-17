package ru.tinkoff.academy.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.academy.exceptions.EntityNotFoundException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler({DataIntegrityViolationException.class, NumberFormatException.class,
            NullPointerException.class, IllegalAccessException.class})
    public ResponseEntity<?> handleIllegalArgsException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class, EntityNotFoundException.class})
    public ResponseEntity<?> handleNotFoundById(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
