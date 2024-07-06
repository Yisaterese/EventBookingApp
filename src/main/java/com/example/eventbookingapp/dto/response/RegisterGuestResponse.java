package com.example.eventbookingapp.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterGuestResponse {
    private String message;
    private Long guestId;
}
