package ru.skypro.exception;

public class AbsentItemException extends RuntimeException{

    public AbsentItemException(String message) {
        super(message);
    }
}
