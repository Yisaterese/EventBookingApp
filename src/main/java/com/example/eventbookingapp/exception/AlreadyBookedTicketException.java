package com.example.eventbookingapp.exception;

public class AlreadyBookedTicketException extends RuntimeException{
    public AlreadyBookedTicketException(String message) {
        super(message);
    }
}
