package com.example.eventbookingapp.service;

import com.example.eventbookingapp.data.model.Event;
import com.example.eventbookingapp.data.model.Guest;
import com.example.eventbookingapp.dto.request.CreateEventRequest;
import com.example.eventbookingapp.dto.request.MakeReservationRequest;
import com.example.eventbookingapp.dto.request.PurchaseTicketRequest;
import com.example.eventbookingapp.dto.response.MakeReservationResponse;
import com.example.eventbookingapp.dto.response.PurchaseTicketResponse;

import java.util.List;

public interface EventService {

    Event   createEvent(CreateEventRequest createEventRequest);

    Event getEvent(Long id);

    MakeReservationResponse makeReservation(MakeReservationRequest makeReservationRequest);


    PurchaseTicketResponse purchaseTicket(PurchaseTicketRequest purchaseTicketRequest);

    List<Event> getEventByOrganiser(Long id);
}
