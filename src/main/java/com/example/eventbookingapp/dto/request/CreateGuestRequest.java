package com.example.eventbookingapp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateGuestRequest {
    private String email;
    private String userName;
    private Long eventId;
}
