package com.example.eventbookingapp.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MakeReservationResponse {
    private Long ticketId;
    private String message;
}
