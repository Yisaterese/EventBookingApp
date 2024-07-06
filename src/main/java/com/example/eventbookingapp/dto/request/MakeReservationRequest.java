package com.example.eventbookingapp.dto.request;

import com.example.eventbookingapp.data.model.CATEGORY;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MakeReservationRequest {
    private Long eventId;
    private CATEGORY type;
    private Long guestId;
}
