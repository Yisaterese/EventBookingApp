package com.example.eventbookingapp.service;

import com.example.eventbookingapp.data.model.Event;
import com.example.eventbookingapp.data.model.Guest;
import com.example.eventbookingapp.data.model.Organiser;
import com.example.eventbookingapp.data.model.ROLE;
import com.example.eventbookingapp.data.repository.EventRepository;
import com.example.eventbookingapp.data.repository.GuestRepository;
import com.example.eventbookingapp.data.repository.OrganiserRepository;
import com.example.eventbookingapp.dto.request.RegisterOrganiserRequest;
import com.example.eventbookingapp.dto.request.AddTicketRequest;
import com.example.eventbookingapp.dto.request.CreateEventRequest;
import com.example.eventbookingapp.dto.request.CreateGuestRequest;
import com.example.eventbookingapp.dto.response.AddTicketResponse;
import com.example.eventbookingapp.dto.response.CreateEventResponse;
import com.example.eventbookingapp.dto.response.CreateGuestResponse;
import com.example.eventbookingapp.dto.response.RegisterOrganiserResponse;
import com.example.eventbookingapp.exception.OrganiserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.example.eventbookingapp.data.model.ROLE.PARTICIPANT;

@Service
public class OrganiserServiceImpl implements OrganiserService {

    private final ModelMapper modelMapper;
    private final EventService eventService;
    private final TicketService ticketService;
    private final GuestRepository guestRepository;
    private final EventRepository eventRepository;
    private final OrganiserRepository organiserRepository;

    public OrganiserServiceImpl(ModelMapper modelMapper,
                                EventService eventService, TicketService ticketService, GuestRepository guestRepository,
                                EventRepository eventRepository, OrganiserRepository organiserRepository) {
        this.modelMapper = modelMapper;
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.guestRepository = guestRepository;
        this.eventRepository = eventRepository;
        this.organiserRepository = organiserRepository;
    }

    @Override
    public RegisterOrganiserResponse registerOrganiser(RegisterOrganiserRequest request) {
        if(organiserRepository.existsByEmail(request.getEmail()))throw new IllegalArgumentException("Email taken");
        Organiser organiser = modelMapper.map(request, Organiser.class);
        organiserRepository.save(organiser);
        RegisterOrganiserResponse response = modelMapper.map(organiser, RegisterOrganiserResponse.class);
        response.setMessage("Registered successfully");
     return response;
    }

    public CreateGuestResponse addGuest(CreateGuestRequest createGuestRequest) {
        Event event = getEvent(createGuestRequest.getEventId());
        Guest newGuest = modelMapper.map(createGuestRequest, Guest.class);
        newGuest.setRoleAs(PARTICIPANT);
        newGuest = guestRepository.save(newGuest);
        event.getGuests().add(newGuest);
        eventRepository.save(event);
        CreateGuestResponse response = modelMapper.map(newGuest, CreateGuestResponse.class);
        response.setMessage("guest added successfully");
        System.out.println(response);
        return response;
    }

    @Override
    public AddTicketResponse addTicket(AddTicketRequest request){
        return  ticketService.addTicket(request);
    }



    private Event getEvent(Long id) {
        return eventService.getEvent(id);
    }
    @Override
    public CreateEventResponse createEvent(CreateEventRequest createEventRequest){
            Organiser organiser = getOrganiserById(createEventRequest.getOrganiserId());
            Event newEvent = eventService.createEvent(createEventRequest);
            organiser.getEvents().add(newEvent);
            organiserRepository.save(organiser);
            CreateEventResponse response = modelMapper.map(newEvent, CreateEventResponse.class);
            response.setMessage("Event created successfully");
            return response;
    }

    @Override
    public Organiser getOrganiserById(Long id) {
         return organiserRepository
                 .findById(id)
                 .orElseThrow(()->new OrganiserNotFoundException("Organiser not found"));
    }
}
