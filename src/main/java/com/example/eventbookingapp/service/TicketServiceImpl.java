package com.example.eventbookingapp.service;

import com.example.eventbookingapp.data.model.CATEGORY;
import com.example.eventbookingapp.data.model.Event;
import com.example.eventbookingapp.data.model.Ticket;
import com.example.eventbookingapp.data.repository.EventRepository;
import com.example.eventbookingapp.data.repository.TicketRepository;
import com.example.eventbookingapp.dto.request.AddTicketRequest;
import com.example.eventbookingapp.dto.response.AddTicketResponse;
import com.example.eventbookingapp.dto.utility.TicketNumber;
import com.example.eventbookingapp.exception.NoTicketBooked;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{
    private final TicketNumber ticketNumber = new TicketNumber(); ;
    private final ModelMapper modelMapper;
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final EventService eventService;
    public TicketServiceImpl(ModelMapper modelMapper, TicketRepository ticketRepository, @Lazy EventRepository eventRepository, EventService eventService) {
        this.modelMapper = modelMapper;
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.eventService = eventService;
    }

    @Override
    @Transactional
    public AddTicketResponse addTicket(AddTicketRequest addTicketRequest) {
        Event scheduledEvent = eventService.getEvent(addTicketRequest.getEventId());
        Ticket newTicket = modelMapper.map(addTicketRequest, Ticket.class);
        newTicket.setTicketNumber(ticketNumber.generateTicketNumber());
        ticketRepository.save(newTicket);
        scheduledEvent.getTickets().add(newTicket);
        eventRepository.save(scheduledEvent);
        AddTicketResponse response = modelMapper.map(newTicket, AddTicketResponse.class);
        response.setMessage("successfully added");
        return response;
    }
    @Override
    public BigDecimal getDiscountedPrice(BigDecimal originalAmount, BigDecimal discount){
        return originalAmount.multiply(discount).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
    }
    @Override
    public List<Ticket> viewAllTicketsForAnEvent(Long eventId){
        Event scheduledEvent = eventService.getEvent(eventId);
        if(scheduledEvent == null)throw new NoTicketBooked("no ticket booked for");
        return new ArrayList<>(scheduledEvent.getTickets());
    }
    @Override
    public Ticket getTicketById(Long ticketId){
       return ticketRepository.findAll().stream()
                .filter(ticket -> ticket.getId().equals(ticketId))
                .findFirst()
                .orElseThrow(()-> new NoTicketBooked("Ticket not found"));
    }

    @Override
    public Ticket getTicketByCategory(CATEGORY category){
        System.out.println(ticketRepository.findAll());
        return ticketRepository.findAll().stream()
                .filter(ticket -> ticket.getCategory().equals(category))
                .findFirst()
                .orElseThrow(()-> new NoTicketBooked("No tickets available for this category"));
    }
}
