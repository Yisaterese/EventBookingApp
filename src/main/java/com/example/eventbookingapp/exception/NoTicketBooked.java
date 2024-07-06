package com.example.eventbookingapp.exception;

public class NoTicketBooked extends RuntimeException {
    public NoTicketBooked(String message) {
        super(message);
    }
}
