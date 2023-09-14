package ru.railshop.onlineshop.exception;

import lombok.Getter;
import ru.railshop.onlineshop.validator.Error;

import java.util.List;

public class ValidationException extends RuntimeException  {
    @Getter
    private final List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}
