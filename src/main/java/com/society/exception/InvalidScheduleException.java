package com.society.exception;

public class InvalidScheduleException extends RuntimeException {
    public InvalidScheduleException(String message) {
        super(message);
    }
}
