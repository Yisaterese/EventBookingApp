package com.example.eventbookingapp.exception;

public class AttendeeNotFoundException extends RuntimeException {
    public AttendeeNotFoundException(String message) {
        super(message);
    }
}
