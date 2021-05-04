package ru.isaykin.app.exceptions;

import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = NoteNotFoundException.class)
    public ResponseEntity<Object> handleNoteNotFoundException(NoteNotFoundException exception) {
        return getResponseEntityWithBody(NOT_FOUND, exception);

    }

    private ResponseEntity<Object> getResponseEntityWithBody(HttpStatus status, Exception exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.toString());
        body.put("message", exception.getMessage());
        return new ResponseEntity<>(
                body, status);
    }

    @ExceptionHandler(value = PersonNotFoundException.class)
    public ResponseEntity<Object> handlePersonNotFoundException(PersonNotFoundException exception) {
        return getResponseEntityWithBody(NOT_FOUND, exception);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {
        ApiError apiError = new ApiError(status, "Not valid value of field was entered.");
        apiError.addValidationErrors(exception.getBindingResult().getFieldErrors());
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(value = IncorrectUpdateSemanticsDataAccessException.class)
    public ResponseEntity<Object> handlePersonNotFoundException(IncorrectUpdateSemanticsDataAccessException exception) {
        return getResponseEntityWithBody(BAD_REQUEST, exception);
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleDuplicationConstraintException(SQLIntegrityConstraintViolationException exception) {
        return getResponseEntityWithBody(CONFLICT, exception);
    }
}
