package ru.isaykin.app.exceptions;

public class InvalidNoteException extends RuntimeException {
    public InvalidNoteException(String message) {
        super(message);
    }
}
