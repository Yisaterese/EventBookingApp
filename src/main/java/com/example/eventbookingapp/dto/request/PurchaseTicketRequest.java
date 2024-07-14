package com.example.eventbookingapp.dto.request;

import com.example.eventbookingapp.data.model.CATEGORY;
import com.example.eventbookingapp.data.model.TicketStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static com.example.eventbookingapp.data.model.TicketStatus.SOLD;

@Getter
@Setter
public class PurchaseTicketRequest {
    private Long eventId;
    private CATEGORY category;
    private String purchaserMail;
    private BigDecimal amount;
}
