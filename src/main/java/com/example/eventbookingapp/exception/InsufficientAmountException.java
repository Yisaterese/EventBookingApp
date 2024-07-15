package com.example.eventbookingapp.exception;

public class InsufficientAmountException extends EventBookingException{
    public InsufficientAmountException(String message) {
        super(message);
    }
}
