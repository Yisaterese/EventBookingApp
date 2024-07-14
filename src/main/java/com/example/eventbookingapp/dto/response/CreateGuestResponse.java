package com.example.eventbookingapp.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CreateGuestResponse {
    private Long guestId;
    private String message;

}
