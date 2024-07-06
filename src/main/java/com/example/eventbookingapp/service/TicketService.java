package com.example.eventbookingapp.service;

import com.example.eventbookingapp.data.model.Ticket;
import com.example.eventbookingapp.dto.request.AddTicketRequest;
import com.example.eventbookingapp.dto.response.AddTicketResponse;

import java.math.BigDecimal;
import java.util.List;

public interface TicketService {
    AddTicketResponse addTicket(AddTicketRequest addTicketRequest);

    BigDecimal getDiscountedPrice(BigDecimal originalAmount, BigDecimal discount);


    List<Ticket> viewAllTicketsForAnEvent(Long eventId);

    Ticket getTicketById(Long eventId, Long ticketId);
}
