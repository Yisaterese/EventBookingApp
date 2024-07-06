package com.example.eventbookingapp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterAttendeeResponse {
    private String message;
    private Long attendeeId;
}
