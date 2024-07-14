package com.example.eventbookingapp.exception;

public class InsufficientAmountException extends RuntimeException{
    public InsufficientAmountException(String message) {
        super(message);
    }
}
