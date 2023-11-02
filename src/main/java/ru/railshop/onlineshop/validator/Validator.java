package ru.railshop.onlineshop.validator;

public interface Validator<T> {
    ValidateResult isValid(T object);
}
