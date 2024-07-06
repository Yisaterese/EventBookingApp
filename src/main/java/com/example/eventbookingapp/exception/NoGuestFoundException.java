package com.example.eventbookingapp.exception;

public class NoGuestFoundException extends RuntimeException{
    public NoGuestFoundException(String message) {
        super(message);
    }
}
