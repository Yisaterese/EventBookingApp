package com.example.eventbookingapp.dto.response;

import com.example.eventbookingapp.data.model.TicketStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static com.example.eventbookingapp.data.model.TicketStatus.SOLD;

@Setter
@Getter
public class PurchaseTicketResponse {
    private Long ticketId;
    private String ticketNumber;
    private BigDecimal amount;
    private String purchaserMail;
    private String eventName;
    private TicketStatus ticketStatus = SOLD;

}
