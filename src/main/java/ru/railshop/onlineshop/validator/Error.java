package ru.railshop.onlineshop.validator;

import lombok.Value;

@Value(staticConstructor = "of")
public class Error {
     String error;
     String message;
}
