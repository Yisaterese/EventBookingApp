package com.example.eventbookingapp.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateEventResponse {
    private Long eventId;
    private String message;
}
