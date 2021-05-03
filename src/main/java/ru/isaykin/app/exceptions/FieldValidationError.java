package ru.isaykin.app.exceptions;

import lombok.Data;

@Data
public class FieldValidationError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

}
