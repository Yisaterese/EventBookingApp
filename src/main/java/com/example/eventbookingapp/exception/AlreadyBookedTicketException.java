package com.example.eventbookingapp.exception;

public class AlreadyBookedTicketException extends EventBookingException{
    public AlreadyBookedTicketException(String message) {
        super(message);
    }
}
