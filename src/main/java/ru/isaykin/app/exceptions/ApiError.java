package ru.isaykin.app.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SameParameterValue")
@Data
public class ApiError {

    private LocalDateTime timestamp;
    private String status;
    private String message;
    private List<FieldValidationError> fieldValidationErrors;

    ApiError(HttpStatus status, String message) {
        timestamp = LocalDateTime.now();
        this.status = status.toString();
        this.message = message;
    }

    void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(error -> {
            FieldValidationError subError = new FieldValidationError();
            subError.setField(error.getField());
            subError.setMessage(error.getDefaultMessage());
            subError.setRejectedValue(error.getRejectedValue());
            subError.setObject(error.getObjectName());
            this.addSubError(subError);
        });
    }

    private void addSubError(FieldValidationError subError) {
        if (fieldValidationErrors == null) {
            fieldValidationErrors = new ArrayList<>();
        }
        fieldValidationErrors.add(subError);
    }
}
