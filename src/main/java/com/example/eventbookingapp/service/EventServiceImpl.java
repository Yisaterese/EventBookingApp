package com.example.eventbookingapp.service;

import com.example.eventbookingapp.data.model.CATEGORY;
import com.example.eventbookingapp.data.model.Event;
import com.example.eventbookingapp.data.model.Guest;
import com.example.eventbookingapp.data.model.Ticket;
import com.example.eventbookingapp.data.repository.EventRepository;
import com.example.eventbookingapp.dto.request.CreateEventRequest;
import com.example.eventbookingapp.dto.request.MakeReservationRequest;
import com.example.eventbookingapp.dto.request.PurchaseTicketRequest;
import com.example.eventbookingapp.dto.response.MakeReservationResponse;
import com.example.eventbookingapp.dto.response.PurchaseTicketResponse;
import com.example.eventbookingapp.exception.EventNotFoundException;
import com.example.eventbookingapp.exception.InsufficientAmountException;
import com.example.eventbookingapp.exception.NoGuestFoundException;
import com.example.eventbookingapp.exception.AlreadyBookedTicketException;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.eventbookingapp.data.model.TicketStatus.*;

@Service
public class EventServiceImpl implements EventService{
    private final ModelMapper modelMapper;
    private final EventRepository eventRepository;
    private  final TicketService ticketService ;
    private final GuestService guestService;

    public EventServiceImpl(ModelMapper modelMapper, EventRepository eventRepository, @Lazy TicketService ticketService, GuestService guestService) {
        this.modelMapper = modelMapper;
        this.eventRepository = eventRepository;
        this.ticketService = ticketService;
        this.guestService = guestService;
    }


    @Override
    public Event createEvent(CreateEventRequest createEventRequest){
        Event event = modelMapper.map(createEventRequest, Event.class);
        return eventRepository.save(event);
    }
    @Override
    public Event getEvent(Long id){
        return eventRepository.findById(id)
                .orElseThrow(()->new EventNotFoundException("Event not found"));

    }


    @Override
    @Transactional
    public MakeReservationResponse makeReservation(MakeReservationRequest makeReservationRequest){

        Event event = getEvent(makeReservationRequest.getEventId());
        Guest attendee = findGuest(makeReservationRequest.getGuestId());
        Ticket createdTicket = getTicket(makeReservationRequest.getTicketType());
        createdTicket.setCategory(makeReservationRequest.getTicketType());
        createdTicket.setTicketStatus(RESERVED);
//        attendee.setReservationStatus(true);
        attendee.getTickets().add(createdTicket);
        attendee.setEvent(event);
        event.getAttendees().add(attendee);
        event.getGuests().add(attendee);
//        createdTicket.setGuest(attendee);
        eventRepository.save(event);
        System.out.println(event);
        MakeReservationResponse response = modelMapper.map(createdTicket,MakeReservationResponse.class) ;
        response.setMessage("Reservation successful");
        return response;
    }

    @Override
    public PurchaseTicketResponse purchaseTicket(PurchaseTicketRequest purchaseTicketRequest){
        Ticket ticket = getTicket(purchaseTicketRequest.getCategory());
        Guest guest = getGuestBy(purchaseTicketRequest.getPurchaserMail());
        Event scheduledEvent = getEvent(purchaseTicketRequest.getEventId());
        validateTicket(purchaseTicketRequest, ticket);
        ticket.setTicketStatus(SOLD);
        ticket.setPurchaserMail(purchaseTicketRequest.getPurchaserMail());
        ticket.setEvent(scheduledEvent);
        ticket.setGuest(guest);
        guest.getTickets().add(ticket);
        guest.setEvent(scheduledEvent);
        scheduledEvent.getTickets().add(ticket);
        scheduledEvent.getGuests().add(guest);
        eventRepository.save(scheduledEvent);
//        System.out.println(ticket);
        PurchaseTicketResponse response = modelMapper.map(ticket, PurchaseTicketResponse.class);
        response.setEventName(scheduledEvent.getTitle());
        return response;
    }


    private static void validateTicket(PurchaseTicketRequest purchaseTicketRequest, Ticket ticket) {
        if(!ticket.getTicketStatus().equals(AVAILABLE)) throw new AlreadyBookedTicketException("No ticket available for purchase");
        if(ticket.getAmount().compareTo(purchaseTicketRequest.getAmount()) < 0){throw new InsufficientAmountException("inadequate funds to book ticket");}
    }

    private Guest getGuestBy(String username){
        return guestService.getGuestByEmail(username);
    }

    private Guest getGuestBy(Long attendeeId) {
        return guestService.getGuest(attendeeId);
    }

    private Guest findGuest(Long id){
       return guestService.getGuest(id);
    }

    private  Guest getGuest(MakeReservationRequest makeReservationRequest, Event event) {
        return event.getGuests().stream()
                .filter(guest -> guest.getGuestId()
                        .equals(makeReservationRequest.getGuestId()))
                .findFirst()
                .orElseThrow(()->new NoGuestFoundException("no guest found"));
    }


    private Ticket getTicket(CATEGORY makeReservationRequest) {
        return ticketService.getTicketByCategory(makeReservationRequest);
    }


    @Override
    public List<Event> getEventByOrganiser(Long id) {
        return eventRepository.findAll();
    }
}
