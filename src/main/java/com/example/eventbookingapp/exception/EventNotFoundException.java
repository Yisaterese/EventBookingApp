package com.example.eventbookingapp.exception;

import java.lang.constant.ConstantDesc;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String message) {
        super(message);
    }
}
