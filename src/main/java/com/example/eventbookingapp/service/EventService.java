package com.example.eventbookingapp.service;

import com.example.eventbookingapp.data.model.Event;
import com.example.eventbookingapp.dto.request.CreateEventRequest;
import com.example.eventbookingapp.dto.request.MakeReservationRequest;
import com.example.eventbookingapp.dto.response.MakeReservationResponse;

import java.util.List;

public interface EventService {

    Event   createEvent(CreateEventRequest createEventRequest);

    Event getEvent(Long id);

    MakeReservationResponse makeReservation(MakeReservationRequest makeReservationRequest);

    List<Event> getEventByOrganiser(Long id);
}
