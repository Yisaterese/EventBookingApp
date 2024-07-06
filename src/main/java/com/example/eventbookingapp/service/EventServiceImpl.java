package com.example.eventbookingapp.service;

import com.example.eventbookingapp.data.model.Event;
import com.example.eventbookingapp.data.model.Guest;
import com.example.eventbookingapp.data.model.Ticket;
import com.example.eventbookingapp.data.repository.EventRepository;
import com.example.eventbookingapp.dto.request.CreateEventRequest;
import com.example.eventbookingapp.dto.request.MakeReservationRequest;
import com.example.eventbookingapp.dto.response.MakeReservationResponse;
import com.example.eventbookingapp.exception.EventNotFoundException;
import com.example.eventbookingapp.exception.NoGuestFoundException;
import com.example.eventbookingapp.exception.NoTicketBooked;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService{
    private final ModelMapper modelMapper;
    private final EventRepository eventRepository;
    private final GuestService guestService;

    public EventServiceImpl(ModelMapper modelMapper,
                            EventRepository eventRepository,
                            GuestService guestService) {
        this.modelMapper = modelMapper;
        this.eventRepository = eventRepository;
        this.guestService = guestService;
    }

    @Override
    public Event createEvent(CreateEventRequest createEventRequest){
        Event event = modelMapper.map(createEventRequest, Event.class);
        return eventRepository.save(event);
    }
    @Override
    public Event    getEvent(Long id){
        return eventRepository.findById(id)
                .orElseThrow(()->new EventNotFoundException("Event not found"));
    }

@Override
    public MakeReservationResponse makeReservation(MakeReservationRequest makeReservationRequest){
        Event event = getEvent(makeReservationRequest.getEventId());
        Guest attendee = getGuest(makeReservationRequest, event);
        Ticket createdTicket = getTicket(makeReservationRequest, event, attendee);
        createdTicket.setType(makeReservationRequest.getType());
        event.getAttendees().add(attendee);
        eventRepository.save(event);
        MakeReservationResponse response = modelMapper.map(createdTicket,MakeReservationResponse.class) ;
        response.setMessage("Reservation successful");
        return response;
    }

    private static Guest getGuest(MakeReservationRequest makeReservationRequest, Event event) {
        return event.getGuests().stream()
                .filter(guest -> guest.getGuestId()
                        .equals(makeReservationRequest.getGuestId()))
                .findFirst().orElseThrow(()->new NoGuestFoundException("no guest found"));
    }

    private static Ticket getTicket(MakeReservationRequest makeReservationRequest, Event event, Guest attendee) {
        Ticket createdTicket = event.getTickets().stream().filter(ticket -> ticket.getId()
                .equals(makeReservationRequest.getGuestId()))
                        .findFirst().orElseThrow(()-> new NoTicketBooked("No tickets"));
        attendee.getTickets().add(createdTicket);
        return createdTicket;
    }


    @Override
    public List<Event> getEventByOrganiser(Long id) {
        return eventRepository.findAll();
    }
}
