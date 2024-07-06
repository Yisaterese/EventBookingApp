package com.example.eventbookingapp.service;

import com.example.eventbookingapp.data.model.Organiser;
import com.example.eventbookingapp.dto.request.RegisterOrganiserRequest;
import com.example.eventbookingapp.dto.request.AddTicketRequest;
import com.example.eventbookingapp.dto.request.CreateEventRequest;
import com.example.eventbookingapp.dto.response.AddTicketResponse;
import com.example.eventbookingapp.dto.response.CreateEventResponse;
import com.example.eventbookingapp.dto.response.RegisterOrganiserResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrganiserService {
    RegisterOrganiserResponse registerOrganiser(RegisterOrganiserRequest request);

    AddTicketResponse addTicket(AddTicketRequest request);

    CreateEventResponse createEvent(CreateEventRequest createEventRequest);

    Organiser getOrganiserById(Long id);
}
